package com.example.demo.kafka.test.service;

public interface DemoProducer {

	public void sendMessage(String key, String msg) throws Exception;

}
