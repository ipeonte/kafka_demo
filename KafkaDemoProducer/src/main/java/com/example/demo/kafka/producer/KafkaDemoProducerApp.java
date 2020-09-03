package com.example.demo.kafka.producer;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.kafka.producer.service.DemoProducer;

@SpringBootApplication
public class KafkaDemoProducerApp implements CommandLineRunner {
	
	private final int SLEEP_TIME = 500;
	
	private boolean terminated = false;
	
	@Autowired
	private DemoProducer producer;
	
	private static final Logger LOG = LoggerFactory.getLogger("ProducerDemo");
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoProducerApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (!terminated) {
			producer.sendMessage("Hello");
			Thread.sleep(SLEEP_TIME);
		}
	}

	@PreDestroy
    public void onDestroy() throws Exception {
		LOG.info("Kafka Demp Producer closed.");
		LOG.info("Total " + producer.getSetntCount() + " messages sent.");
		terminated = true;
    }
}
