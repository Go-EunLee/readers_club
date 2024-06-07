package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.domain.User.User;
import org.example.domain.User.UserJoinRequest;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Test
    @DisplayName("유저 가입 성공")
    void createUserSuccess(){
        // Given
        UserJoinRequest joinUser = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user = joinUser.toEntity("1234");  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정

        // When
        given(userRepository.save(any())).willReturn(user);
        userService.join(joinUser);

        // Then
        when(userRepository.findByEmail("go@gmail.com")).thenReturn(Optional.of(user));
        User findUser = userRepository.findByEmail("go@gmail.com").orElseThrow(NullPointerException::new);
        Assertions.assertThat(findUser.getName()).isEqualTo(user.getName());

    }

    @Test
    @DisplayName("유저 가입 성공, 찾기 실패")
    void createUserSuccessAndFindFail(){
        // Given
        UserJoinRequest joinUser = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user = joinUser.toEntity("1234");  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정

        // When
        given(userRepository.save(any())).willReturn(user);
        userService.join(joinUser);

        // Then
        when(userRepository.findByEmail("go@gmail.com")).thenReturn(Optional.of(user));
        User findUser = userRepository.findByEmail("go@gmail.com").orElseThrow(NullPointerException::new);
        Assertions.assertThat(findUser.getName()).isNotEqualTo("g");

    }

    @Test
    @DisplayName("가입한 유저 리스트")
    void userList(){
        // Given
        UserJoinRequest joinUser1 = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user1 = joinUser1.toEntity("1234");  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정
        UserJoinRequest joinUser2 = new UserJoinRequest("ho", "ho@gmail.com", "1234", "ho", 14, "010-1111-1111");
        User user2 = joinUser2.toEntity("1234");

        // When
        given(userRepository.save(any())).willReturn(user1);
        userService.join(joinUser1);
        given(userRepository.save(any())).willReturn(user2);
        userService.join(joinUser2);

        when(userRepository.findByEmail("go@gmail.com")).thenReturn(Optional.of(user1));
        User findUser = userRepository.findByEmail("go@gmail.com").get();
        Assertions.assertThat(findUser.getName()).isEqualTo(user1.getName());

        // Then
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(user1);
        expectedUsers.add(user2);

        when(userRepository.findAll()).thenReturn(expectedUsers);
        List<User> actualUsers = userService.users();
        Assertions.assertThat(actualUsers).isEqualTo(expectedUsers);

    }

/*    @Test
    @DisplayName("로그인 성공")
    void loginSuccess(){
        // Given
        UserJoinRequest joinUser = new UserJoinRequest("go", "go@gmail.com", "1234", "go", 24, "010-1111-1111");
        User user = joinUser.toEntity(encoder.encode(joinUser.getPassword()));  // 암호화된 비밀번호를 넣어야 하지만 지금은 임시로 비밀번호 지정

        given(userRepository.save(any())).willReturn(user);
        userService.join(joinUser);

        // When
        given(userRepository.existsByEmail(any())).willReturn(true);
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));
        String success = userService.login("go@gmail.com", "1234");

        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));
        User findUser = userRepository.findByEmail("go@gmail.com").orElseThrow(NullPointerException::new);
        System.out.println(findUser.getEmail());
        System.out.println(joinUser.getPassword());
        System.out.println(encoder.encode(joinUser.getPassword()));

        // Then
        Assertions.assertThat(success).isEqualTo("로그인 성공");
    }*/
}