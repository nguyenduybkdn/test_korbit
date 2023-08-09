package com.aptech.sem4eprojectbe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.sem4eprojectbe.entity.NewsTblEntity;

@Repository
public interface NewsTblRepository extends JpaRepository<NewsTblEntity, String> {
    public List<NewsTblEntity> findByDeletedIsFalse();
}
