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
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "newstbl")
public class NewsTblEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @NotNull
    private LocalDateTime createat;
    
    private String image;
    @NotBlank
    private String content;
    @NotBlank
    private String title;
    @NotNull
    private Boolean deleted;
}
