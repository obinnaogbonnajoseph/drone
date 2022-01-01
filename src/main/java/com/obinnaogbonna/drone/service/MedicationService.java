package com.obinnaogbonna.drone.service;

import com.obinnaogbonna.drone.dto.MedicationDto;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;

public interface MedicationService {

    public Medication save(MedicationDto medication) throws IllegalArgumentException;

    public Medication findById(long id) throws ResourceNotFoundException;

    public void delete(long id) throws ResourceNotFoundException, IllegalArgumentException;

    public Medication update(MedicationDto dto) throws ResourceNotFoundException, IllegalArgumentException;

    public Medication imageUpdate(Long id, byte[] imageData) throws ResourceNotFoundException, IllegalArgumentException;

    public Drone checkLoadedMedications(long droneId) throws ResourceNotFoundException;

}
