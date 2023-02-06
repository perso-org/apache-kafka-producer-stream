package com.tfa.service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.tfa.stream.WikiMediaChangesHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WikiMediaProducer {

	@Value("${apache.kafka.topic.name}")
	private String topic;

	private final KafkaTemplate<String, String> template;
	
	public void envoie() throws Exception {
		EventHandler handler = new WikiMediaChangesHandler(template, topic);

		String url= "https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource eventSource = new EventSource.Builder(handler, URI.create(url)).build();
		eventSource.start();
		TimeUnit.MINUTES.sleep(100);
		handler.onClosed();
	}
}
