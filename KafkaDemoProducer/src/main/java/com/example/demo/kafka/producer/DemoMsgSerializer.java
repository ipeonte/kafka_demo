package com.example.demo.kafka.producer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.kafka.shared.DemoMsg;

public class DemoMsgSerializer implements Serializer<DemoMsg> {

	private static final Logger LOG = LoggerFactory.getLogger("ProducerDemo");
	
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// Do nothing
	}

	@Override
	public byte[] serialize(String topic, DemoMsg data) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream obj = null;
		
		try {
			obj = new ObjectOutputStream(out);
			obj.writeObject(data);
			obj.flush();
		} catch (IOException e) {
			LOG.error("Error serializing object " + data);
		}
		
		try {
			obj.close();
		} catch (IOException e) {
			// Ignore
		}
		
		return out.toByteArray();
	}

	@Override
	public void close() {
		// Do nothing
	}

}
