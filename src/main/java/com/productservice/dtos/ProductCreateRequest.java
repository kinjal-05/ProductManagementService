package com.productservice.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductCreateRequest
{
	@NotBlank
	private String title;

	private String description;

	@NotBlank
	private String author;

	@NotBlank
	private String isbn;

	@NotNull
	@Min(1)
	@Max(1000)
	private Double price;

	@NotNull
	private Long categoryId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public ProductCreateRequest(String title, String description, String author, String isbn, Double price, Long categoryId) {
		this.title = title;
		this.description = description;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.categoryId = categoryId;
	}

	public ProductCreateRequest() {
	}
}