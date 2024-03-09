package org.mapmark.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {


    @NotBlank(message = "Invalid username: username is empty")
    @NotNull(message = "Invalid username: username is NULL")
    private String username;


    @NotBlank(message = "Invalid password: password is empty")
    @NotNull(message = "Invalid password: password is NULL")
    private String password;

}
