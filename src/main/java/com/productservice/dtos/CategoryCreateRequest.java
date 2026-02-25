package com.productservice.dtos;

import jakarta.validation.constraints.*;

public class CategoryCreateRequest
{
	@NotBlank(message = "Category name is required")
	@Size(max = 30)
	private String name;

	@NotNull(message = "Display order is required")
	@Min(value = 1, message = "Display order must be at least 1")
	@Max(value = 100, message = "Display order must not exceed 100")
	private Integer displayOrder;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public CategoryCreateRequest(String name, Integer displayOrder) {
		this.name = name;
		this.displayOrder = displayOrder;
	}

	public CategoryCreateRequest() {
	}
}