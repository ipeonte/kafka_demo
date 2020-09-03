package com.example.demo.kafka.producer.test;

import java.util.HashMap;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import com.example.demo.kafka.producer.DemoMsgSerializer;
import com.example.demo.kafka.shared.DemoMsg;


@Configuration
@EnableAutoConfiguration
public class DemoProducerAppTestsConfig {

	@Autowired
	private EmbeddedKafkaBroker broker;

	@Bean
	public KafkaTemplate<String, DemoMsg> kafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(
				new HashMap<>(KafkaTestUtils.producerProps(broker)), 
				new StringSerializer(), new DemoMsgSerializer()));
	}
}
