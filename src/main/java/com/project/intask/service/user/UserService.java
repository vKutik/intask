package com.example.test.service.user;

import com.example.test.dto.user.UserDto;
import com.example.test.exceptions.UserAlreadyExistException;
import com.example.test.model.Board;
import com.example.test.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    User registerUser(User user) throws UserAlreadyExistException;

    User update(User user, Long id);
    void delete(User user);

    User getByUsername(String username);

    User getById(Long id);

    List<User> findAll();

}
