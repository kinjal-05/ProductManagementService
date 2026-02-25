package com.productservice.servicesimpl;

import static com.productservice.dtos.ProductSpecification.hasAuthor;
import static com.productservice.dtos.ProductSpecification.hasCategory;
import static com.productservice.dtos.ProductSpecification.hasIsbn;
import static com.productservice.dtos.ProductSpecification.hasTitle;
import static com.productservice.dtos.ProductSpecification.priceGreaterThanOrEqual;
import static com.productservice.dtos.ProductSpecification.priceLessThanOrEqual;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.productservice.dtos.ProductCreateRequest;
import com.productservice.exceptions.BadRequestException;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.kafka.ProductEventProducer;
import com.productservice.kafkadtos.ProductEvent;
import com.productservice.models.Category;
import com.productservice.models.Product;
import com.productservice.repositories.CategoryRepository;
import com.productservice.repositories.ProductRepository;
import com.productservice.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final ProductEventProducer producer;

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
			ProductEventProducer producer) {
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.producer = producer;
	}

	@Override
	public Product createProduct(ProductCreateRequest product) {
		Category category = categoryRepository.findById(product.getCategoryId()).orElseThrow(
				() -> new ResourceNotFoundException("Category not found with id: " + product.getCategoryId()));
		Product p = new Product();
		p.setTitle(product.getTitle());
		p.setDescription(product.getDescription());
		p.setAuthor(product.getAuthor());
		p.setIsbn(product.getIsbn());
		p.setPrice(product.getPrice());
		p.setCategory(category);
		ProductEvent event = new ProductEvent();
		event.setTitle(product.getTitle());
		event.setDescription(product.getDescription());
		event.setAuthor(product.getAuthor());
		event.setIsbn(product.getIsbn());
		event.setPrice(product.getPrice());
		event.setCategoryName(category.getName());

		producer.sendProductEvent(event);
		return productRepository.save(p);
	}

	@Override
	public Page<Product> getAllProducts(Pageable pageable) {
		return productRepository.findAll(pageable);
	}

	@Override
	public Product updateProduct(Long id, ProductCreateRequest product) {
		Product p = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		Category c = categoryRepository.findById(product.getCategoryId()).orElseThrow(
				() -> new ResourceNotFoundException("Category not found with id: " + product.getCategoryId()));
		p.setTitle(product.getTitle());
		p.setAuthor(product.getAuthor());
		p.setDescription(product.getDescription());
		p.setIsbn(product.getIsbn());
		p.setPrice(product.getPrice());
		p.setCategory(c);

		return productRepository.save(p);
	}

	@Override
	public void deleteProduct(Long id) {
		if (!productRepository.existsById(id)) {
			throw new ResourceNotFoundException("Product not found with id: " + id);
		}

		productRepository.deleteById(id);
	}

	@Override
	public Page<Product> searchProducts(String title, String author, String isbn, Long categoryId, Double minPrice,
			Double maxPrice, Pageable pageable) {

		if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
			throw new BadRequestException("minPrice cannot be greater than maxPrice");
		}

		Specification<Product> spec = Specification.where(hasTitle(title)).and(hasAuthor(author)).and(hasIsbn(isbn))
				.and(hasCategory(categoryId)).and(priceGreaterThanOrEqual(minPrice))
				.and(priceLessThanOrEqual(maxPrice));

		Page<Product> products = productRepository.findAll(spec, pageable);

		if (products.isEmpty()) {
			throw new ResourceNotFoundException("No products found with given search criteria");
		}

		return products;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
	}

}