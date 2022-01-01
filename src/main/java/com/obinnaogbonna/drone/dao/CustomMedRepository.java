package com.obinnaogbonna.drone.dao;

import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;

public interface CustomMedRepository {

    public Drone checkLoadedMedications(long droneId) throws ResourceNotFoundException;

}
