package com.aptech.sem4eprojectbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.sem4eprojectbe.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    public List<ProductEntity> findByDeletedIsFalse();

    public List<ProductEntity> findByAlcoholLessThanEqual(String alcoholLevel);

    public Optional<List<ProductEntity>> findByCategoryid(String id);

    public Optional<List<ProductEntity>> findByNameLikeIgnoreCaseAndDeletedIsFalse(String name);

    public List<ProductEntity> findByAlcoholLessThanEqualAndCategoryid(String alcohol, String cateId);
}
