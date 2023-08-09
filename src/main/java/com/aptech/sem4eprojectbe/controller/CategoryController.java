package com.aptech.sem4eprojectbe.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.common.model.ResponseModel;
import com.aptech.sem4eprojectbe.entity.CategoryEntity;
import com.aptech.sem4eprojectbe.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/insert-category")
    public ResponseModel insertCategory(@Valid @RequestBody CategoryEntity category) {
        return new ResponseModel("ok", "success", categoryService.insertCategory(category));

    }

    @GetMapping("/categories")
    public ResponseModel getAllCategory() {
        return new ResponseModel("ok ", "success", categoryService.getAllCategory());
    }

    @PostMapping("/category")
    public ResponseModel getCategoryById(@Valid @RequestBody IdModel idModel) {
        Optional<CategoryEntity> cate = categoryService.getCategoryById(idModel);
        if (cate.isPresent() && !cate.get().getDeleted()) {
            return new ResponseModel("ok", "success", cate.get());
        } else {
            return new ResponseModel("fail", "Can not find id " + idModel.getId(), null);
        }
    }

    @PutMapping("/update-category")
    public ResponseModel updateCategory(@Valid @RequestBody CategoryEntity category) {
        return new ResponseModel("ok", "success", categoryService.updateCategory(category));
    }

    @DeleteMapping("/delete-category")
    public ResponseModel deleteCategory(@Valid @RequestBody IdModel idModel) {
        Optional<CategoryEntity> category = categoryService.getCategoryById(idModel);
        if (category.isPresent()) {
            CategoryEntity deletedCategory = category.get();
            deletedCategory.setDeleted(true);
            categoryService.updateCategory(deletedCategory);
            return new ResponseModel("ok", "success", null);
        } else {
            return new ResponseModel("fail", "Can not find id : " + idModel.getId(), null);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        return new ResponseModel("fail", "Validation Error", exception.getMessage());
    }
}
