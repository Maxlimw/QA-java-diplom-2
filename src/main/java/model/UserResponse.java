package model;

import io.qameta.allure.internal.shadowed.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResponse {
    private boolean success;
    private User user; // Изменено с 'userData' на 'user'
    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("refreshToken")
    private String refreshToken;
    private String message;

    @Data
    public static class User {
        private String email;
        private String name;
    }
}
