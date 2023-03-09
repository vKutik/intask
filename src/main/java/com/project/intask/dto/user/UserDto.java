package com.example.test.dto.user;

import com.example.test.validation.PasswordMatches;
import com.example.test.model.Role;
import com.example.test.model.User;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class UserDto {
    private Long id;
    @NotEmpty
    @NotNull
    private String username;
    @NotNull
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotNull
    @NotEmpty(message = "Matching password should not be empty")
    private String matchingPassword;

    private Set<Role> roles;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roles = user.getRoles();
    }

    public User toModel() {
        User user = new User();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRoles(this.roles);
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDto)) {
            return false;
        }

        UserDto userDto = (UserDto) o;

        if (!getId().equals(userDto.getId())) {
            return false;
        }
        if (!getUsername().equals(userDto.getUsername())) {
            return false;
        }
        if (!getPassword().equals(userDto.getPassword())) {
            return false;
        }
        if (!getMatchingPassword().equals(userDto.getMatchingPassword())) {
            return false;
        }
        return getRoles().equals(userDto.getRoles());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getMatchingPassword().hashCode();
        result = 31 * result + getRoles().hashCode();
        return result;
    }
}
