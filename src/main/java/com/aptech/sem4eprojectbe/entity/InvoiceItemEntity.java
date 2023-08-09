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
@Table(name = "invoiceitem")
public class InvoiceItemEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotBlank
    private String invoiceid;
    @NotBlank
    private String productid;

    @Positive
    private int quantity;
    @Positive
    private BigDecimal totalprice;
    @NotNull
    private Boolean deleted;
}
