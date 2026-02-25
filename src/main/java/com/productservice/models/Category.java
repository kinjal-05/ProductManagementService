package com.productservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Category
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Category name is required")
	@Size(max=30)
	@Column(nullable = false,length = 30)
	private String name;

	@NotNull
	@Min(1)
	@Max(100)
	@Column(name="display_name")
	private Integer displayOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "category",cascade=CascadeType.ALL,orphanRemoval=true)
	private List<Product> products;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Category(Long id, String name, Integer displayOrder, List<Product> products) {
		this.id = id;
		this.name = name;
		this.displayOrder = displayOrder;
		this.products = products;
	}

	public Category() {
	}
}