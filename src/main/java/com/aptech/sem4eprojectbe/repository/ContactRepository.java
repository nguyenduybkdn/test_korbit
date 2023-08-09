package com.aptech.sem4eprojectbe.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aptech.sem4eprojectbe.entity.ContactEntity;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, String> {
    public List<ContactEntity> findByDeletedIsFalse();
}
