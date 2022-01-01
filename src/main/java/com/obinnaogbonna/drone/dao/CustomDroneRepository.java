package com.obinnaogbonna.drone.dao;

import java.util.List;

import com.obinnaogbonna.drone.entity.Drone;

public interface CustomDroneRepository {

    public List<Drone> findAvailable(int totalWeight);

}
