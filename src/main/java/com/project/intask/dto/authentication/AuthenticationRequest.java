package com.project.intask.dto.authentication;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull
    @Size(min = 4)
    @ApiModelProperty(notes = "username", example = "root", required = true)
    private String username;
    @NotNull
    @Size(min = 6)
    @ApiModelProperty(notes = "password", example = "123123", required = true)
    private String password;
}
