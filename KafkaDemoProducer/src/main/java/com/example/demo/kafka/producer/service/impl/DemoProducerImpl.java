package com.example.demo.kafka.producer.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.demo.kafka.producer.service.DemoProducer;
import com.example.demo.kafka.shared.Constants;
import com.example.demo.kafka.shared.DemoMsg;

@Service
public class DemoProducerImpl implements DemoProducer {

	private static final Logger LOG = LoggerFactory.getLogger("ProducerDemo");

	private int cnt;

	@Autowired
	private KafkaTemplate<String, DemoMsg> kafkaTemplate;

	public String sendMessage(String message) {
		int code = ++cnt;
		return sendMessage(new DemoMsg(code, message));
	}

	public String sendMessage(DemoMsg msg) {
		LOG.debug("Sending messasge into topic " + Constants.TOPIC_NAME + ": " + msg);
		
		Future<SendResult<String, DemoMsg>> result = null;
		try {
			result = this.kafkaTemplate.send(Constants.TOPIC_NAME, msg);
		} catch (Exception e) {
			LOG.error("Error sending message - " + e.getMessage());
			return null;
		}
		
		SendResult<String, DemoMsg> response;
		
		try {
			response = result.get();
			LOG.trace("Message " + response + " sent");
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Error message response - " + e.getMessage());
			return null;
		}
		
		return response.toString();
	}
	
	@Override
	public int getSetntCount() {
		return cnt;
	}
}