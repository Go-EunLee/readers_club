package org.example.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.example.domain.Comment;
import org.example.domain.Great;
import org.example.domain.User.User;
import org.example.domain.enums.BoardCategory;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor // 기본 생성자를 자동으로 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 추가
@Getter
@Builder
@Entity // 실제 DB의 테이블과 매칭될 Class 임을 명시
public class Board{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 50000)
    private String body;

    private String author;
    private String imageUrl;
    private String publishYear;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)  //enum 이름 값을 DB에 저장, ORIGINAL 이면 순서 숫자값을 저장
    private BoardCategory boardCategory;

    @ManyToOne(fetch = FetchType.EAGER)  //LAZY 는 지연로딩, EAGER 는 즉시로딩
    private User user;

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Great> greats;  // 좋아용
    private int greatCount;  // 좋아요 수

    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> comments;
    private int commentCount;

    public void greatChange(int greatCount) {
        this.greatCount = greatCount;
    }

    public void commentChange(int commentCount) {
        this.commentCount = commentCount;
    }

    public void commentMinus(){
        this.commentCount -= 1;
    }

    public void update(BoardDto dto) {
        this.title = dto.getTitle();
        this.body = dto.getBody();
        this.author = dto.getAuthor();
        this.imageUrl = dto.getImageUrl();
        this.boardCategory = BoardCategory.of(dto.getCategory());
        this.modifiedAt = LocalDateTime.now();
    }
}
