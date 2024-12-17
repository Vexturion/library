package com.library.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LoanRequest {

    @NotBlank
    private String isbn;

    @NotBlank(message = "El userId es obligatorio")
    private String userId;

    // Constructor vacío
    public LoanRequest() {}

    // Constructor con parámetros
    public LoanRequest(String isbn, String userId) {
        this.isbn = isbn;
        this.userId = userId;
    }

    // Getters y Setters
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
