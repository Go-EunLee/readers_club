package org.example.repository;

import org.example.domain.Great;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GreatRepository extends JpaRepository<Great, Long> {
    List<Great> findAllByUserEmail(String email);

    Boolean existsByUserEmailAndBoardId(String email, Long boardId);

    void deleteByUserEmailAndBoardId(String email, Long boardId);
}
