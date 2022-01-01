package com.obinnaogbonna.drone.dao;

import com.obinnaogbonna.drone.entity.Drone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<Drone, Long> {

}
