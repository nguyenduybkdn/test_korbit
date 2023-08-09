package com.aptech.sem4eprojectbe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.common.model.IdModel;
import com.aptech.sem4eprojectbe.entity.CategoryEntity;
import com.aptech.sem4eprojectbe.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity insertCategory(CategoryEntity category) {
        return categoryRepository.save(category);
    }

    public List<CategoryEntity> getAllCategory() {
        return categoryRepository.findByDeletedIsFalse();
    }

    public Optional<CategoryEntity> getCategoryById(IdModel idModel) {
        return categoryRepository.findById(idModel.getId());
    }

    public CategoryEntity updateCategory(CategoryEntity category){
        return categoryRepository.findById(category.getId())
        .map(categoryItem -> {
            categoryItem.setName(category.getName());
            categoryItem.setDeleted(category.getDeleted());
            return categoryRepository.save(categoryItem);
        })
        .orElseGet(() ->{
            CategoryEntity newCate = new CategoryEntity();
            newCate.setName(category.getName());
            newCate.setDeleted(false);
            return categoryRepository.save(newCate);

        });
    }

  
}
