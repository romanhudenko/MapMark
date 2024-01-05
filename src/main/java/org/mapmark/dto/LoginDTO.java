package org.mapmark.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDTO {


    @NotBlank(message = "Invalid UserName: Empty UserName")
    @NotNull(message = "Invalid UserName: UserName is NULL")
    private String username;


    @NotBlank(message = "Invalid Password: Empty Password")
    @NotNull(message = "Invalid Password: Password is NULL")
    private String password;

}
