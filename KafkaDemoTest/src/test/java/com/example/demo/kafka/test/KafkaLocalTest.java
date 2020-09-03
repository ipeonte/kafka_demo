package com.example.demo.kafka.test;


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
import com.example.demo.kafka.test.service.DemoProducer;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:/test.properties")
@EmbeddedKafka(topics = Constants.TOPIC_NAME)
public class KafkaLocalTest {

	private static final Logger LOG = LoggerFactory.getLogger("Test");

	@Autowired
	private EmbeddedKafkaBroker broker;

	@Autowired
	private DemoProducer demo;

	private Consumer<String, String> consumer;

	@Before
	public void setUp() {
		Map<String, Object> configs = new HashMap<>(
				KafkaTestUtils.consumerProps("consumer", "false", broker));
		consumer = new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(),
				new StringDeserializer()).createConsumer();
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
		final String key = "1";
		final String msg = "Test Message";
		
		demo.sendMessage(key, msg);
		
		ConsumerRecord<String, String> record = KafkaTestUtils.
				getSingleRecord(consumer, Constants.TOPIC_NAME);

		assertEquals("Kafka consumer record value doesn't match",key, record.key());
		assertEquals("Kafka consumer record value doesn't match", msg, record.value());
	}
}
