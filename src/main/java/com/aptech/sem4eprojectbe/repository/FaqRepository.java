package com.aptech.sem4eprojectbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.sem4eprojectbe.entity.FaqEntity;

@Repository
public interface FaqRepository extends JpaRepository<FaqEntity, String> {
    public List<FaqEntity> findByDeletedIsFalse();

}
