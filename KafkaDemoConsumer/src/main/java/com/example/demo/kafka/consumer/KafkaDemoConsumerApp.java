package com.example.demo.kafka.consumer;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.demo.kafka.shared.Constants;
import com.example.demo.kafka.shared.DemoMsg;

@SpringBootApplication
public class KafkaDemoConsumerApp implements CommandLineRunner {

	private final int SLEEP_TIME = 500;

	private boolean terminated = false;

	private static final Logger LOG = LoggerFactory.getLogger("ConsumerDemo");

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoConsumerApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (!terminated) {
			Thread.sleep(SLEEP_TIME);
		}
	}

	@KafkaListener(topics = Constants.TOPIC_NAME)
	public void listen(DemoMsg message) {
		LOG.trace("Received messasge from topic " + Constants.TOPIC_NAME + ": " + message);
	}

	@PreDestroy
	public void onDestroy() throws Exception {
		LOG.info("Kafka Demp Consumer closed.");
		terminated = true;
	}
}
