package com.example.demo.kafka.test.service.impl;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.demo.kafka.shared.Constants;
import com.example.demo.kafka.test.service.DemoProducer;

@Service
public class DemoProducerImpl implements DemoProducer {

	public static final Logger LOG = LoggerFactory.getLogger(DemoProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> template;

	@Override
	public void sendMessage(String key, String msg) throws Exception {
		Future<SendResult<String, String>> result = null;
		try {
			result = template.send(Constants.TOPIC_NAME, key, msg);
		} catch (Exception e) {
			LOG.error("Error sending message - " + e.getMessage());
			throw e;
		}
		
		SendResult<String, String> response;
		
		try {
			response = result.get();
			LOG.debug("Message " + response + " sent");
		} catch (Exception e) {
			LOG.error("Error message response - " + e.getMessage());
			throw e;
		}
	}

}
