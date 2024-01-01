package org.mapmark.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {


    @NotBlank(message = "Invalid UserName: Empty UserName")
    @NotNull(message = "Invalid UserName: UserName is NULL")
    private String username;

    @Email
    @NotBlank(message = "Invalid Email: Empty Email")
    @NotNull(message = "Invalid Email: Email is NULL")
    private String email;

    @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String password;

}
