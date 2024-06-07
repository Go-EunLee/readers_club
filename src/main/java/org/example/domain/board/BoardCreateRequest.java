package org.example.domain.board;

import lombok.Data;
import org.example.domain.User.User;
import org.example.domain.enums.BoardCategory;

import java.time.LocalDateTime;

@Data
public class BoardCreateRequest {
    private String title;
    private String body;
    private String publishYear;
    private String author;
    private String imageUrl;
    private BoardCategory category;

    public Board toEntity(User loginUser) {
        return Board.builder()
                .title(title)
                .body(body)
                .publishYear(publishYear)
                .author(author)
                .imageUrl(imageUrl)
                .boardCategory(category)
                .greatCount(0)
                .user(loginUser)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .commentCount(0)
                .build();
    }
}
