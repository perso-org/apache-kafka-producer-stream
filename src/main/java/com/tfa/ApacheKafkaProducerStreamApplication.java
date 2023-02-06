package com.tfa;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tfa.service.WikiMediaProducer;

@SpringBootApplication
public class ApacheKafkaProducerStreamApplication implements CommandLineRunner{

	private WikiMediaProducer producer;
	
	public ApacheKafkaProducerStreamApplication(WikiMediaProducer producer) {
		super();
		this.producer = producer;
	}

	public static void main(String[] args) {
		SpringApplication.run(ApacheKafkaProducerStreamApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		producer.envoie();
	}

}
