package com.productservice.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="products")
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	@Column(nullable = false)
	private String title;

	private String description;

	@NotBlank(message = "Author name is required")
	@Column(nullable = false)
	private String author;

	@NotBlank(message = "Isbn is required")
	@Column(nullable = false)
	private String isbn;

	@NotNull
	@Min(1)
	@Max(1000)
	@Column(name="price")
	private Double price;

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catagory_id",nullable = false)
	private Category category;

	@OneToMany(mappedBy = "product")
	@JsonManagedReference
	private List<ProductImage> images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductImage> getImages() {
		return images;
	}

	public void setImages(List<ProductImage> images) {
		this.images = images;
	}

	public Product(Long id, String title, String description, String author, String isbn, Double price, Category category, List<ProductImage> images) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.category = category;
		this.images = images;
	}

	public Product() {
	}
}