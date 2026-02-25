package com.productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductImageRequest
{
	@NotBlank(message = "Image URL is required")
	private String imageUrl;

	@NotNull(message = "Product ID is required")
	private Long productId;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ProductImageRequest(String imageUrl, Long productId) {
		this.imageUrl = imageUrl;
		this.productId = productId;
	}

	public ProductImageRequest() {
	}
}