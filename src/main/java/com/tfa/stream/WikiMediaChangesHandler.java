package com.tfa.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WikiMediaChangesHandler implements EventHandler {

	private KafkaTemplate<String, String> kafkaTemplate;
	private String topic;
	
	@Autowired
	public WikiMediaChangesHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.topic = topic;
	}

	@Override
	public void onOpen() throws Exception {
		log.info("Flux de données ouvert...");
	}

	@Override
	public void onClosed() throws Exception {
		log.warn("Flux de données fermé...");
	}

	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		log.info("Production de données-{}",messageEvent.getData());
		kafkaTemplate.send(topic,messageEvent.getData());
	}

	@Override
	public void onComment(String comment) throws Exception {
		log.warn("Traitement flux de données...");
	}

	@Override
	public void onError(Throwable t) {
		log.warn("Erreur {}...",t.getMessage());
	}

}
