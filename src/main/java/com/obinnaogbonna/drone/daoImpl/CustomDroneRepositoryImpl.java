package com.obinnaogbonna.drone.daoImpl;

import java.util.List;

import com.obinnaogbonna.drone.dao.AppRepository;
import com.obinnaogbonna.drone.dao.CustomDroneRepository;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.QDrone;
import com.obinnaogbonna.drone.utils.State;
import com.querydsl.jpa.impl.JPAQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomDroneRepositoryImpl implements CustomDroneRepository {

    @Autowired
    private AppRepository appRepository;

    @Override
    public List<Drone> findAvailable(int totalWeight) {
        QDrone drone = QDrone.drone;
        JPAQuery<Drone> droneJpaQuery = appRepository.startJPAQuery(drone);
        droneJpaQuery.where(drone.state.eq(State.IDLE)
                .and(drone.model.value.goe(totalWeight)));
        return droneJpaQuery.fetch();
    }

}
