package org.example.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.User.User;
import org.example.domain.board.Board;

@NoArgsConstructor // 기본 생성자를 자동으로 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 추가
@Getter
@Builder
@Entity // 실제 DB의 테이블과 매칭될 Class 임을 명시
public class Great {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Board board;
}
