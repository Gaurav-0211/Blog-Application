package com.blogapi.service.impl;

import com.blogapi.entity.Category;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CategoryDto;
import com.blogapi.repo.CategoryRepo;
import com.blogapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper mapper;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.mapper.map(categoryDto,Category.class);
        Category saved = this.categoryRepo.save(cat);
        return this.mapper.map(saved, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDescription(categoryDto.getCategoryDescription());
        Category update = this.categoryRepo.save(cat);
        return this.mapper.map(update, CategoryDto.class);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        this.categoryRepo.delete(cat);
        return null;
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
        return this.mapper.map(cat, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> geAllCategory() {
        List<Category> categories = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((cat)-> this.mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }
}
