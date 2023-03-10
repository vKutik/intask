package com.project.intask.service.user;

import com.project.intask.exceptions.UserAlreadyExistException;
import com.project.intask.model.User;
import com.project.intask.repository.UserRepository;
import com.project.intask.service.role.RoleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public User create(User user) {
        user.setRoles(Set.of(roleService.getByName("USER")));
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        if (userRepository.getUserByUsername(user.getUsername()).isEmpty()) {
            return create(user);
        }
        throw new UserAlreadyExistException("Username was used");
    }

    @Override
    public User update(User user, Long id) {
        User userFromDb = getById(user.getId());
        userFromDb.setUsername(user.getUsername());
        userFromDb.setPassword(user.getPassword());
        userFromDb.setRoles(user.getRoles());
        return userFromDb;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getUserByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public User getById(Long id) {
        return userRepository.getUserById(id)
            .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userRepository.findAll());
    }

}
