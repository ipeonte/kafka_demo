package com.example.demo.kafka.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class KafkaDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApp.class, args);
	}

}
