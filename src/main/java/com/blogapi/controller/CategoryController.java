package com.blogapi.controller;

import com.blogapi.payload.CategoryDto;
import com.blogapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto categoryDto){
        CategoryDto category=this.service.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto,@PathVariable Integer catId){
        CategoryDto category=this.service.updateCategory(categoryDto, catId);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> delete(@PathVariable Integer catId){
        return this.service.deleteCategory(catId);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer catId){
        CategoryDto categoryDto=this.service.getCategory(catId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categories = this.service.geAllCategory();
        return ResponseEntity.ok(categories);
    }


}
