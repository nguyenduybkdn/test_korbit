package com.aptech.sem4eprojectbe.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.UuidGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotBlank
    private String name;

    private String image;
    @Positive
    private BigDecimal price;
    @NotBlank
    private String description;
    @NotBlank
    private String categoryid;
    @Positive
    private int alcohol;
    @NotNull
    private Boolean deleted;
}
