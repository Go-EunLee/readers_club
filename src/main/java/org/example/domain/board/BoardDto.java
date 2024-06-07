package org.example.domain.board;

import lombok.Builder;
import lombok.Data;
import org.example.domain.User.User;
import org.example.domain.enums.BoardCategory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto {
    private Long id;
    private String userEmail;
    private String userNickname;
    private String title;
    private String author;
    private String body;
    private String publishYear;
    private String imageUrl;
    private String category;
    private Integer greatCount;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static BoardDto of(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .userEmail(board.getUser().getEmail())
                .userNickname(board.getUser().getNickname())
                .title(board.getTitle())
                .author(board.getAuthor())
                .body(board.getBody())
                .imageUrl(board.getImageUrl())
                .category(board.getBoardCategory().toString())
                .publishYear(board.getPublishYear())
                .greatCount(board.getGreatCount())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }
}
