package com.productservice.kafka;

import com.productservice.kafkadtos.CategoryEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class CategoryEventProducer {
	@Autowired
	private StreamBridge streamBridge;

	public CategoryEventProducer(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	public CategoryEventProducer() {
	}

	public void sendCategoryEvent(CategoryEvent event) {
		streamBridge.send("category-out-0", event);
	}
}
