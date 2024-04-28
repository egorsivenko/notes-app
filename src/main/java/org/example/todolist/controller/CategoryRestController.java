package org.example.todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Categories")
@RequestMapping("api/V2/categories")
public class CategoryRestController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryRestController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Retrieve all categories")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.listAll();
        return ResponseEntity.ok(categoryMapper.toCategoryResponses(categories));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") UUID id) {
        Category category = categoryService.getById(id);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(category));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Create new category")
    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category addedCategory = categoryService.add(categoryMapper.toCategoryEntity(categoryRequest));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryMapper.toCategoryResponse(addedCategory));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Change category name")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") UUID id,
                                                           @Valid @RequestBody CategoryRequest categoryRequest) {
        Category updatedCategory = categoryService.update(id, categoryRequest.getName().strip());
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(categoryMapper.toCategoryResponse(updatedCategory));
    }

    @SecurityRequirement(name = "JWT")
    @Operation(summary = "Delete category by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable("id") UUID id) {
        Category deletedCategory = categoryService.deleteById(id);
        return ResponseEntity.ok(categoryMapper.toCategoryResponse(deletedCategory));
    }
}
