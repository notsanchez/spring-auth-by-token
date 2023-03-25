package com.example.esyfinancebackend.dto;

public class ErrorDTO {

    public ErrorDTO() {
        super();
    }

    private String error;
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDTO(String error, String message) {
        this.error = error;
        this.message = message;
    }

}
