package com.obinnaogbonna.drone.service;

import java.util.List;

import com.obinnaogbonna.drone.dto.DroneDto;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.utils.RequirementNotMetException;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;

public interface DroneService {

    public Drone register(DroneDto drone) throws IllegalArgumentException, RequirementNotMetException;

    public List<Drone> findAvailable(int totalWeight);

    public Drone load(List<Medication> medications, Long droneId)
            throws ResourceNotFoundException, IllegalArgumentException, RequirementNotMetException;

    public Drone findById(long id) throws ResourceNotFoundException;

    public void delete(long id) throws ResourceNotFoundException, IllegalArgumentException;

    public Drone update(DroneDto drone) throws ResourceNotFoundException, IllegalArgumentException;

    public List<Medication> getMedications(long droneId) throws ResourceNotFoundException;
}
