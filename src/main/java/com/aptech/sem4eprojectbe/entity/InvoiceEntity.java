package com.aptech.sem4eprojectbe.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "invoice")
public class InvoiceEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotNull
    private LocalDateTime createat;
    @Positive
    private BigDecimal totalprice;
    @NotBlank
    private String status;
    @NotBlank
    private String userid;
    @NotNull
    private Boolean deleted;

}
