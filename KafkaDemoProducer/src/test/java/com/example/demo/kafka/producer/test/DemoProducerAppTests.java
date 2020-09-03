package com.example.demo.kafka.producer.test;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.kafka.shared.Constants;
import com.example.demo.kafka.shared.DemoMsg;
import com.example.demo.kafka.shared.DemoMsgDeserializer;
import com.example.demo.kafka.producer.service.DemoProducer;
import com.example.demo.kafka.producer.service.impl.DemoProducerImpl;

@SpringBootTest(classes = { DemoProducerAppTestsConfig.class, DemoProducerImpl.class })
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/test.properties")
@EmbeddedKafka(topics = Constants.TOPIC_NAME)
public class DemoProducerAppTests {

	private static final Logger LOG = LoggerFactory.getLogger("Test");

	@Autowired
	private EmbeddedKafkaBroker broker;

	@Autowired
	private DemoProducer demo;

	private Consumer<String, DemoMsg> consumer;

	@Before
	public void setUp() {
		Map<String, Object> configs = new HashMap<>(
				KafkaTestUtils.consumerProps("consumer", "false", broker));
		consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(),
				new DemoMsgDeserializer()).createConsumer();
		broker.consumeFromAllEmbeddedTopics(consumer);
	}

	@After
	public void tearDown() {
		consumer.close();
	}

	@Test
	public void test() throws Exception {
		LOG.debug("Test starting ...");

		// Send message to the topic
		final DemoMsg msg = new DemoMsg(1, "Test Message");
		
		demo.sendMessage(msg);
		
		ConsumerRecord<String, DemoMsg> record = KafkaTestUtils.
				getSingleRecord(consumer, Constants.TOPIC_NAME);

		assertEquals("Kafka consumer record value doesn't match", msg, record.value());
	}
}
