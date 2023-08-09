package com.aptech.sem4eprojectbe.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "faq")
public class FaqEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotBlank
    private String question;
    @NotBlank
    private String answer;
    @NotNull
    private Boolean deleted;
}
