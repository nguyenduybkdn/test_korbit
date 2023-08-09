package com.aptech.sem4eprojectbe.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contact")
public class ContactEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    @NotBlank
    private String address;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotNull
    private Boolean deleted;

}
