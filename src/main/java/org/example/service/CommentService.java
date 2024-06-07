package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Comment;
import org.example.domain.CommentCreateRequest;
import org.example.domain.User.User;
import org.example.domain.board.Board;
import org.example.repository.BoardRepository;
import org.example.repository.CommentRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Comment> findMyComment(String email) {
        return commentRepository.findAllByUserEmail(email);
    }

    public Comment findComment(Long commentId) { return commentRepository.findById(commentId).orElseThrow(NullPointerException::new);}

    public List<Comment> findAll(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    public void writeComment(Long boardId, CommentCreateRequest req, String email) {
        log.info("댓글 서비스 게시글 번호 : " + boardId.toString());
        Board board = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        User user = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);
        log.info("댓글 카운트 추가 전 : " + board.getCommentCount());
        board.commentChange(board.getCommentCount()+1);

        commentRepository.save(req.toEntity(board, user));
        log.info("댓글 카운트 추가 후 : " + board.getCommentCount());
    }

    @Transactional
    public Long editComment(Long commentId, String newBody, String email) {
        Optional<Comment> optComment = commentRepository.findById(commentId);
        Optional<User> optUser = userRepository.findByEmail(email);
        if (optComment.isEmpty() || optUser.isEmpty() || !optComment.get().getUser().equals(optUser.get())) {
            return null;
        }

        Comment comment = optComment.get();
        comment.update(newBody);
        log.info("수정 후 새 댓글 내용 : " + comment.getBody());
        return comment.getBoard().getId();
    }

    public Long deleteComment(Long commentId, String email) {
        Comment optComment = commentRepository.findById(commentId).orElseThrow(NullPointerException::new);
        User optUser = userRepository.findByEmail(email).orElseThrow(NullPointerException::new);

        Board board = optComment.getBoard();
        board.commentMinus();

        commentRepository.delete(optComment);
        return board.getId();
    }
}
