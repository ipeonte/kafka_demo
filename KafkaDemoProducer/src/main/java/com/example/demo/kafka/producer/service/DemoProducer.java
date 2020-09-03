package com.example.demo.kafka.producer.service;

import com.example.demo.kafka.shared.DemoMsg;

public interface DemoProducer {

	/**
	 * Send message with auto-incremented code
	 * 
	 * @param message Message to send.
	 * 
	 * @return Send acknowledgement
	 */
    String sendMessage(String message);
    
    /**
	 * Send demo message
	 * 
	 * @param message Demo message to send.
	 * 
	 * @return Send acknowledgement
	 */
    String sendMessage(DemoMsg msg);
    
    /**
     * Get number of sent messages
     * 
     * @return The number of sent messages
     */
    int getSetntCount();
}