package com.obinnaogbonna.drone.cron;

import java.util.List;

import com.obinnaogbonna.drone.dao.AuditDroneRepository;
import com.obinnaogbonna.drone.dao.DroneRepository;
import com.obinnaogbonna.drone.entity.AuditDrone;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.utils.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledBatteryCheck {

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private AuditDroneRepository aDroneRepository;

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void batteryCheck() {
        List<Drone> drones = droneRepository.findAll();
        drones.stream().forEach(drone -> {
            try {
                int batteryCapacity = drone.getBatteryCapacity();
                State state = drone.getState();
                AuditDrone aDrone = new AuditDrone(state, batteryCapacity);
                aDrone.setDroneId(drone.getId());
                aDroneRepository.save(aDrone);
            } catch (IllegalArgumentException exc) {
                throw new IllegalArgumentException("Drone not found");
            }
        });
    }

}
