package dev.sigit.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    private int httpCode;

    private T errors;
}
