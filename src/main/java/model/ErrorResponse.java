package model;

import lombok.Data;

@Data
public class ErrorResponse {
    private boolean success;
    private String message;
}
