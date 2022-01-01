package com.obinnaogbonna.drone.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.obinnaogbonna.drone.entity.Drone;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MedicationDto {

    private long id;

    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[\\w-]+$")
    private String name;

    @NotNull(message = "Weight is required")
    private int weight;

    @Pattern(regexp = "^[A-Z0-9_]{11,15}$")
    private String code;

    private Drone drone;

    public MedicationDto(String name, int weight, String code) {
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

}
