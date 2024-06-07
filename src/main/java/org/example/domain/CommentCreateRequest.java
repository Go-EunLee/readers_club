package org.example.domain;

import lombok.Data;
import org.example.domain.User.User;
import org.example.domain.board.Board;

import java.time.LocalDateTime;

@Data
public class CommentCreateRequest {
    private String body;

    public Comment toEntity(Board board, User user){
        return Comment.builder()
                .user(user)
                .board(board)
                .body(body)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .build();
    }
}
