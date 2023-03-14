package com.project.intask.dto.registration;

import com.project.intask.validation.MatchPassword;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@MatchPassword(filed = "password", filedMatch = "confirmPassword")
public class RegistrationRequest {

    @NotNull
    @Size(min = 4)
    @ApiModelProperty(notes = "username", example = "root", required = true)
    private String username;
    @NotNull
    @Size(min = 6)
    @ApiModelProperty(notes = "password", example = "123123", required = true)
    private String password;

    @ApiModelProperty(notes = "confirmPassword", example = "123123", required = true)
    private String confirmPassword;

}
