package org.example.domain.User;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class UserTest {
    @Test
    @DisplayName("유저가 생성되는지 확인")
    void createUser(){
        // Given
        UserJoinRequest joinUser = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user = joinUser.toEntity("1234");  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정

        // When & Then
        Assertions.assertThat(user.getName()).isEqualTo("go");
    }

}