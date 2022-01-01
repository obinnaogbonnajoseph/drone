package com.obinnaogbonna.drone.dao;

import com.obinnaogbonna.drone.entity.Medication;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
