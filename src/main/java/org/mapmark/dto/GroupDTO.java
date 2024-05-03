package org.mapmark.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDTO {


    @NotBlank(message = "Invalid name: name is empty")
    @NotNull(message = "Invalid name: name is NULL")
    private String name;

    private String description;

    private String icon;


}
