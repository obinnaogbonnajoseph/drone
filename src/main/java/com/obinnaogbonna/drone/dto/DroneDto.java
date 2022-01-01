package com.obinnaogbonna.drone.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.entity.Model;
import com.obinnaogbonna.drone.entity.SerialNumber;
import com.obinnaogbonna.drone.utils.State;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DroneDto {

    @NotNull()
    private Model model;

    private Long id;

    private SerialNumber serialNumber;

    private int batteryCapacity;

    private State state;

    private List<Medication> medications;

}
