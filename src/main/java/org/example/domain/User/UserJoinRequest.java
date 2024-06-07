package org.example.domain.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    private String nickname;
    private String email;
    private String password;
    private String myName;
    private int age;
    private String phoneNumber;

    public User toEntity(String encodedPassword){
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .myName(myName)
                .nickname(nickname)
                .age(age)
                .phoneNumber(phoneNumber)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
