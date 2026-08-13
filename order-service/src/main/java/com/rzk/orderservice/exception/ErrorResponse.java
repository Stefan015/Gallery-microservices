package com.rzk.orderservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String message;
}