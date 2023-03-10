package com.project.intask.dto.user;

import com.project.intask.model.Role;
import com.project.intask.model.User;
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
public class UserDto {
    private Long id;
    @NotEmpty
    @NotNull
    private String username;
    @NotNull
    @NotEmpty(message = "Password should not be empty")
    private String password;
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
        return getRoles().equals(userDto.getRoles());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getRoles().hashCode();
        return result;
    }
}
