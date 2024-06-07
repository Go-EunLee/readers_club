package org.example.repository;

import org.assertj.core.api.Assertions;
import org.example.domain.User.User;
import org.example.domain.User.UserJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("유저 저장")
    void saveUser(){
        // Given
        UserJoinRequest joinUser = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user = joinUser.toEntity("1234");  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정

        // When
        User savedUser = userRepository.save(user);

        // Then
        Assertions.assertThat(savedUser.getName()).isEqualTo(user.getName());
    }

}