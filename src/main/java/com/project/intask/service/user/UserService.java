package com.project.intask.service.user;

import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.model.User;
import java.util.List;

public interface UserService {

    User create(User user);

    User registerUser(User user) throws UserAlreadyExistException;

    User update(User user, Long id);

    void delete(User user);

    User getByUsername(String username);

    User getById(Long id);

    List<User> findAll();

}
