package com.aptech.sem4eprojectbe.entity;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class FeedbackEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @Column(name = "userid")
    @NotBlank
    private String userId;
    @NotBlank
    private String content;
    @NotNull
    private LocalDateTime createat;

    @Column(name = "productid")
    @NotBlank
    private String productId;

    @Column(name = "firstname")
    @NotBlank
    private String firstName;

    @Column(name = "lastname")
    @NotBlank
    private String lastName;
    @NotNull
    private Boolean deleted;
}
