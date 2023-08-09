package com.aptech.sem4eprojectbe.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseModel {
    private String status;
    private String message;
    private Object responseObject;
}
