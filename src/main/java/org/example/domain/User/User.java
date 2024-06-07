package org.example.domain.User;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.board.Board;
import org.example.domain.Comment;
import org.example.domain.Great;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 가입할 때 작성할 내용
    private String nickname;
    private String email;
    private String password;
    private String myName;
    private int age;
    private String phoneNumber;

    // 가입하게 되면 자동 생성될 내용
    private LocalDateTime createdAt;

    //mappedBy - 양방향 관계 설정시 관계의 주체가 되는 쪽에서 정의
    //orphanRemoval - 관계 Entity 에서 변경이 일어난 경우 DB 변경을 같이 할지 결정
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Great> greats;


    public void edit(String password, UserDto userDto) {
        this.nickname = userDto.getNickname();
        this.password = password;
        this.myName = userDto.getMyName();
        this.age = userDto.getAge();
        this.phoneNumber = userDto.getPhoneNumber();
    }


}
