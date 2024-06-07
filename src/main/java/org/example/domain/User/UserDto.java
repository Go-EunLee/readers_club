package org.example.domain.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String nickname;
    private String nowPassword;
    private String newPassword;
    private String newPasswordCheck;
    private String myName;
    private int age;
    private String phoneNumber;

    public static UserDto of(User user){
        return UserDto.builder()
                .nickname(user.getNickname())
                .myName(user.getMyName())
                .age(user.getAge())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
