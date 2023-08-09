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
@Table(name = "usertbl")
public class UserEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String address;
    @NotBlank
    private String district;
    @NotBlank
    private String city;
    @NotBlank
    private String role;
    @NotBlank
    private String phone;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotNull
    private Boolean deleted;
}
