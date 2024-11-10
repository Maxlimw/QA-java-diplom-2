package model;

import lombok.Data;

@Data
public class UserData extends UserCredentials {
    private String name;

    public UserData(String email, String password, String name) {
        super(email, password);
        this.name = name;
    }
}
