package org.example.domain.User;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
