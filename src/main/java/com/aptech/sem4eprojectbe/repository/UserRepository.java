package com.aptech.sem4eprojectbe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.sem4eprojectbe.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public List<UserEntity> findByDeletedIsFalse();

    public Optional<UserEntity> findByEmail(String email);
}
