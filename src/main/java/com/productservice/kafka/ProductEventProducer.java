package com.productservice.kafka;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

import com.productservice.kafkadtos.ProductEvent;

@Component
public class ProductEventProducer {

	private final StreamBridge streamBridge;

	public ProductEventProducer(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	public void sendProductEvent(ProductEvent event) {
		System.out.println("🔥 Message received: " + event);
		boolean sent = streamBridge.send("product-out-0", event);
		System.out.println("✅ Sent status: " + sent);
	}

}
