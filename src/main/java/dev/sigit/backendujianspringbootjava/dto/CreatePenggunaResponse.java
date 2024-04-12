package dev.sigit.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class CreatePenggunaResponse<T> {

    private String message;
    private T data;
}
