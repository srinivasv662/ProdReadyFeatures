package com.company.sri.prod_ready_features.prod_ready_features.advice;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
// <T> it can be of generic type
// Parameterized class
public class ApiResponse<T> {

    // since "timestamp": "2026-07-23T19:41:26.0470767" is TZ time format
//    @JsonFormat(pattern = "hh-mm-ss dd-MM-yyy")
    private LocalDateTime timestamp;

    private T data;

    private ApiError error; // if there is error then data will be null, if there is data then error will be null

    public ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this();
        this.data = data;
    }

    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
