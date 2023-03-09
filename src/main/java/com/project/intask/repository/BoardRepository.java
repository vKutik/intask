package com.example.test.repository;


import com.example.test.model.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> getBoardById(Long id);

    void deleteById(Long id);
}
