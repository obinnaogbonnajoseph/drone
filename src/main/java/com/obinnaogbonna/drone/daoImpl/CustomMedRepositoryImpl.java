package com.obinnaogbonna.drone.daoImpl;

import java.util.Optional;

import com.obinnaogbonna.drone.dao.AppRepository;
import com.obinnaogbonna.drone.dao.CustomMedRepository;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.entity.QMedication;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMedRepositoryImpl implements CustomMedRepository {

    @Autowired
    private AppRepository appRepository;

    @Override
    public Drone checkLoadedMedications(long droneId) throws ResourceNotFoundException {
        QMedication medication = QMedication.medication;
        JPAQuery<Medication> mJpaQuery = appRepository.startJPAQuery(medication);
        Optional<Medication> med = Optional.ofNullable(mJpaQuery.where(medication.drone.id.eq(droneId)).fetchFirst());
        return med.orElseThrow(() -> new ResourceNotFoundException("Drone could not be found")).getDrone();
    }

}
