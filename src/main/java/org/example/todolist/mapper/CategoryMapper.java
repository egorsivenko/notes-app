package org.example.todolist.mapper;

import org.example.todolist.controller.request.CategoryRequest;
import org.example.todolist.controller.response.CategoryResponse;
import org.example.todolist.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public Category toCategoryEntity(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        return category;
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    public List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }
}
