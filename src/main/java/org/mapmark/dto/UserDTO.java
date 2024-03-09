package org.mapmark.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {


    @NotBlank(message = "Invalid username: username is empty")
    @NotNull(message = "Invalid username: username is NULL")
    private String username;

    @Email
    @NotBlank(message = "Invalid email: email is empty")
    @NotNull(message = "Invalid email: email is NULL")
    private String email;

    @NotBlank(message = "Invalid password: password is empty")
    @NotNull(message = "Invalid password: password is NULL")
    private String password;

}
