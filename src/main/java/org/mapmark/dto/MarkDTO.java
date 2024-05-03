package org.mapmark.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkDTO {


    @NotBlank(message = "Invalid name: name is empty")
    @NotNull(message = "Invalid name: name is NULL")
    private String name;

    @DecimalMax(value = "90.000000", message = "Please enter a valid latitude")
    @DecimalMin(value = "-90.000000", message = "Please enter a valid latitude")
    private double latitude;

    @DecimalMax(value = "180.000000", message = "Please enter a valid longitude")
    @DecimalMin(value = "-180.000000", message = "Please enter a valid longitude")
    private double longitude;

    @Size(min = 6, max = 6, message = "Wrong HEX code")
    @Pattern(regexp = "^[0-9a-fA-F]+$", message = "Wrong HEX pattern")
    private String colorHex;

}
