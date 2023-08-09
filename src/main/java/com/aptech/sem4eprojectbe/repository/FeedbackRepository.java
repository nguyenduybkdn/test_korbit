package com.aptech.sem4eprojectbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.sem4eprojectbe.entity.FeedbackEntity;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, String> {
    public List<FeedbackEntity> findByDeletedIsFalse();

    public Optional<List<FeedbackEntity>> findByProductIdAndDeletedIsFalse(String productId);
}
