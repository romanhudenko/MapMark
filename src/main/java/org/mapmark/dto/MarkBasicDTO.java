package org.mapmark.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarkBasicDTO {


    @NotBlank(message = "Invalid Name: Empty name")
    @NotNull(message = "Invalid Name: Name is NULL")
    private String name;

    @DecimalMax(value = "90.000000", message = "Please Enter a valid Latitude")
    @DecimalMin(value = "-90.000000", message = "Please Enter a valid Latitude")
    private double latitude;

    @DecimalMax(value = "180.000000", message = "Please Enter a valid Longitude")
    @DecimalMin(value = "-180.000000", message = "Please Enter a valid Longitude")
    private double longitude;

    @Size(min = 6, max = 6, message = "Wrong HEX size")
    @Pattern(regexp = "^[0-9a-fA-F]+$", message = "Wrong HEX pattern")
    private String colorHex;

    private int groupId;
}
