package org.example.todolist.controller;

import jakarta.validation.Valid;
import org.example.todolist.controller.request.CategoryRequest;
import org.example.todolist.controller.response.CategoryResponse;
import org.example.todolist.entity.Category;
import org.example.todolist.mapper.CategoryMapper;
import org.example.todolist.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/V2/categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryRestController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.listAll();
        return ResponseEntity.ok(categoryMapper.toCategoryResponses(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") UUID id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(category));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category addedCategory = categoryService.add(categoryMapper.toCategoryEntity(categoryRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryMapper.toCategoryResponse(addedCategory));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateCategory(@PathVariable("id") UUID id, @Valid @RequestBody CategoryRequest categoryRequest) {
        categoryService.update(id, categoryRequest.getName());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") UUID id) {
        categoryService.deleteById(id);
    }
}
