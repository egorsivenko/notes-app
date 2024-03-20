package org.example.todolist.controller;

import org.example.todolist.controller.form.CategoryForm;
import org.example.todolist.entity.Category;
import org.example.todolist.exception.CategoryNameAlreadyExists;
import org.example.todolist.mapper.CategoryMapper;
import org.example.todolist.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("api/V1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/list")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.listAll());
        return "category/categories";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        CategoryForm categoryForm = new CategoryForm();
        model.addAttribute("categoryForm", categoryForm);
        return "category/add_category";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("categoryForm") CategoryForm categoryForm, Model model) {
        try {
            categoryService.add(categoryMapper.toCategoryEntity(categoryForm));
        } catch (CategoryNameAlreadyExists e) {
            model.addAttribute("error", e.getMessage());
            return "category/add_category";
        }
        return "redirect:/api/V1/categories/list";
    }

    @GetMapping("/edit")
    public String editCategoryForm(@RequestParam("id") UUID id, Model model) {
        Category category = categoryService.getById(id);
        model.addAttribute("categoryForm", categoryMapper.toCategoryForm(category));
        return "category/edit_category";
    }

    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("categoryForm") CategoryForm categoryForm, Model model) {
        try {
            categoryService.update(categoryForm.getId(), categoryForm.getName().strip());
        } catch (CategoryNameAlreadyExists e) {
            model.addAttribute("error", e.getMessage());
            return "category/edit_category";
        }
        return "redirect:/api/V1/categories/list";
    }

    @PostMapping("/delete")
    public String deleteCategory(@RequestParam("id") UUID id) {
        categoryService.deleteById(id);
        return "redirect:/api/V1/categories/list";
    }
}
