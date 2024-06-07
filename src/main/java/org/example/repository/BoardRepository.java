package org.example.repository;

import org.example.domain.board.Board;
import org.example.domain.enums.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findByTitle(String title);

    Page<Board> findAllByBoardCategory(BoardCategory boardCategory, Pageable pageable);
    List<Board> findAllByBoardCategory(BoardCategory boardCategory);

    Page<Board> findAllByBoardCategoryAndTitleContainingOrAuthorContaining(BoardCategory boardCategory, String title, String author, Pageable pageable);
    Page<Board> findAllByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);

    @Query("SELECT b FROM Board b WHERE b.boardCategory = :category AND (b.title LIKE %:keyword% OR b.author LIKE %:keyword%)")
    Page<Board> findByBoardCategoryAndTitleOrAuthor(BoardCategory category, String keyword, Pageable pageable);

    List<Board> findAllByUserEmail(String email);
}
