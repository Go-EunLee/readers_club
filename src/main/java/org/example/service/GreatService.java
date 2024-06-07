package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.Great;
import org.example.domain.User.User;
import org.example.domain.board.Board;
import org.example.repository.BoardRepository;
import org.example.repository.GreatRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GreatService {
    private final GreatRepository greatRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Great> findMyGreat(String email) {
        return greatRepository.findAllByUserEmail(email);
    }

    public Boolean checkGreat(String email, Long boardId) {
        return greatRepository.existsByUserEmailAndBoardId(email, boardId);
    }

    @Transactional
    public void addLike(String email, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        User loginUser = userRepository.findByEmail(email).get();

        board.greatChange(board.getGreatCount() + 1);

        greatRepository.save(Great.builder()
                .user(loginUser)
                .board(board)
                .build());
    }

    @Transactional
    public void deleteLike(String email, Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        User loginUser = userRepository.findByEmail(email).get();
        User boardUser = board.getUser();

        board.greatChange(board.getGreatCount() - 1);

        greatRepository.deleteByUserEmailAndBoardId(email, boardId);
    }

    public Boolean checkLike(String email, Long boardId) {
        return greatRepository.existsByUserEmailAndBoardId(email, boardId);
    }
}
