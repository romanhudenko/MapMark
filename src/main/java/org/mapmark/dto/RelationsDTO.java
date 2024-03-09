package org.mapmark.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RelationsDTO {

    @NotBlank(message = "Invalid name: name is empty")
    @NotNull(message = "Invalid name: name is NULL")
    private String markId;
    @NotNull(message = "Invalid groupId: groupId is NULL")
    private Long groupId;

}
