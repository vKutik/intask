package com.example.test.repository;


import com.example.test.model.Board;
import com.example.test.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String username);

    Optional<User> getUserById(Long id);

    List<User> getUsersByUsername(String username);

}
