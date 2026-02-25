package com.productservice.servicesimpl;

import com.productservice.dtos.CategoryCreateRequest;
import com.productservice.dtos.CategorySpecifications;
import com.productservice.exceptions.ResourceNotFoundException;
import com.productservice.kafka.CategoryEventProducer;
import com.productservice.kafka.ProductEventProducer;
import com.productservice.kafkadtos.CategoryEvent;
import com.productservice.models.Category;
import com.productservice.repositories.CategoryRepository;
import com.productservice.services.CategoryService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryEventProducer categoryEventProducer;

	public CategoryServiceImpl(CategoryRepository categoryRepository,CategoryEventProducer categoryEventProducer) {
		this.categoryRepository = categoryRepository;
		this.categoryEventProducer=categoryEventProducer;
	}



	@Override
	public Category create(CategoryCreateRequest request) {
		try {

			Category category = new Category();

			category.setName(request.getName());

			category.setDisplayOrder(request.getDisplayOrder());
			CategoryEvent categoryEvent=new CategoryEvent();
			categoryEvent.setRequestId(category.getId());
			categoryEvent.setCategoryName(category.getName());
			categoryEvent.setDescription("New Category Created");
			categoryEventProducer.sendCategoryEvent(categoryEvent);
			return categoryRepository.save(category);

		} catch (DataIntegrityViolationException ex) {
			throw new RuntimeException(
					"Failed to create category due to database constraint violation",
					ex
			);

		} catch (Exception ex) {
			throw new RuntimeException(
					"Unexpected error while creating category",
					ex
			);
		}
	}


	@Override
	public Page<Category> getAll(Pageable pageable) {
		try {

			return categoryRepository.findAll(pageable);

		} catch (Exception ex) {
			throw new RuntimeException("Failed to fetch categories", ex);
		}
	}


	@Override
	public Category getById(Long id) {

		return categoryRepository.findById(id)

				.orElseThrow(() ->
						new ResourceNotFoundException("Category not found with id: " + id)
				);
	}


	@Override
	public Category update(Long id, CategoryCreateRequest request) {


		Category existingCategory = categoryRepository.findById(id)

				.orElseThrow(() ->
						new ResourceNotFoundException("Category not found with id: " + id)
				);

		existingCategory.setName(request.getName());

		existingCategory.setDisplayOrder(request.getDisplayOrder());

		return categoryRepository.save(existingCategory);
	}


	@Override
	public void delete(Long id) {

		Category category = categoryRepository.findById(id)

				.orElseThrow(() ->
						new ResourceNotFoundException("Category not found with id: " + id)
				);

		categoryRepository.delete(category);
	}


	@Override
	public Page<Category> search(
			String name,
			Integer displayOrder,
			Pageable pageable) {

		Specification<Category> spec =
				CategorySpecifications.build(name, displayOrder);

		return categoryRepository.findAll(spec, pageable);
	}

}