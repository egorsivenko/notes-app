package org.example.todolist.service;

import org.example.todolist.entity.Category;
import org.example.todolist.exception.CategoryNameAlreadyExists;
import org.example.todolist.exception.CategoryNotFoundException;
import org.example.todolist.exception.InsufficientPrivilegesException;
import org.example.todolist.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    public List<Category> listAll() {
        String username = userService.getCurrentUsername();
        return categoryRepository.findByUsernameInCreationOrder(username);
    }

    public Category getById(UUID id) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        String username = userService.getCurrentUsername();
        if (userService.isCurrentUserNotAdmin() && !categoryCreatedByUser(category, username)) {
            throw new InsufficientPrivilegesException(username);
        }
        return category;
    }

    public Category getByName(String name) {
        String username = userService.getCurrentUsername();
        return categoryRepository
                .findByName(name, username)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public Category add(Category category) {
        checkIfCategoryWithNameExists(category.getName());

        category.setUser(userService.getCurrentUser());
        return categoryRepository.save(category);
    }

    public Category update(UUID id, String name) {
        Category category = getById(id);
        if (!Objects.equals(category.getName(), name)) {
            checkIfCategoryWithNameExists(name);

            category.setName(name);
            return categoryRepository.save(category);
        }
        return category;
    }

    public Category deleteById(UUID id) {
        Category category = getById(id);
        categoryRepository.delete(category);
        return category;
    }

    private void checkIfCategoryWithNameExists(String name) {
        String username = userService.getCurrentUsername();
        Optional<Category> categoryByName = categoryRepository.findByName(name, username);
        if (categoryByName.isPresent()) {
            throw new CategoryNameAlreadyExists(name);
        }
    }

    private boolean categoryCreatedByUser(Category category, String username) {
        return Objects.equals(category.getUser().getUsername(), username);
    }
}
