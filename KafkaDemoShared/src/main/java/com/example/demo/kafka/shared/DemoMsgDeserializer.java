package com.example.demo.kafka.shared;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoMsgDeserializer implements Deserializer<DemoMsg> {

	private static final Logger LOG = LoggerFactory.getLogger("ConsumerDemo");
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// Do nothing
	}

	@Override
	public DemoMsg deserialize(String topic, byte[] data) {
		DemoMsg result = null;
		ObjectInputStream in = null;
		
		try {
			in = new ObjectInputStream(new ByteArrayInputStream(data));
			result = (DemoMsg) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			LOG.error("Error deserializing DataMsg object - " + e.getMessage());
			return null;
		}
		
		try {
			in.close();
		} catch (IOException e) {
			// Ignore
		}
		
		return result;
	}
	
	
	@Override
	public void close() {
		// Do nothing
	}
}
