package com.obinnaogbonna.drone.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.obinnaogbonna.drone.dao.CustomDroneRepository;
import com.obinnaogbonna.drone.dao.DroneRepository;
import com.obinnaogbonna.drone.dao.MedicationRepository;
import com.obinnaogbonna.drone.dao.SerialNumberRepository;
import com.obinnaogbonna.drone.dto.DroneDto;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.entity.Model;
import com.obinnaogbonna.drone.entity.SerialNumber;
import com.obinnaogbonna.drone.serviceImpl.DroneServiceImpl;
import com.obinnaogbonna.drone.utils.ModelEnum;
import com.obinnaogbonna.drone.utils.RequirementNotMetException;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;
import com.obinnaogbonna.drone.utils.State;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class DroneServiceLayerTest {

    @Mock
    private DroneRepository dRepository;

    @Mock
    private CustomDroneRepository cDroneRepository;

    @Mock
    private MedicationRepository mRepository;

    @Mock
    private SerialNumberRepository sNumberRepository;

    @Mock
    private ModelMapper mapper;

    DroneService dService;

    final Drone drone = new Drone();

    final DroneDto dto = new DroneDto();

    @BeforeEach
    void init() {
        dService = new DroneServiceImpl(dRepository, cDroneRepository, mRepository, sNumberRepository, mapper);
        drone.setSerialNumber(new SerialNumber("12345678"));
        drone.setBatteryCapacity(100);
        Model model = new Model(ModelEnum.LIGHT_WEIGHT);
        drone.setModel(model);
        drone.setState(State.IDLE);

        dto.setModel(model);
        dto.setId(1l);
    }

    @Test
    public void shouldRegister_Success() {
        when(dRepository.count()).thenReturn(5l);
        when(dRepository.save(any(Drone.class))).thenReturn(drone);
        Drone savedDrone;
        try {
            savedDrone = dService.register(dto);
            assertEquals(savedDrone.getModel().getValue(), drone.getModel().getValue());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RequirementNotMetException e) {
            e.printStackTrace();
        }
        assertDoesNotThrow(() -> dService.register(dto));
    }

    @Test
    public void shouldRegister_ThrowRequirementNotMetException() {
        when(dRepository.count()).thenReturn(10l);
        assertThrows(RequirementNotMetException.class, () -> dService.register(dto));
    }

    @Test
    public void shouldFindAvailable_Success() {
        List<Drone> availableDrones = Collections.singletonList(drone);
        when(cDroneRepository.findAvailable(anyInt())).thenReturn(availableDrones);
        List<Drone> drones = dService.findAvailable(200);
        assertEquals(drones.size(), availableDrones.size());
    }

    @Test
    public void shouldLoad_Success() {
        List<Drone> availableDrones = Collections.singletonList(this.drone);
        Medication med = new Medication("paracetamol", 50, "ABCDEF_");
        List<Medication> meds = Collections.singletonList(med);
        when(dRepository.findById(anyLong())).thenReturn(Optional.of(this.drone));
        when(cDroneRepository.findAvailable(anyInt())).thenReturn(availableDrones);
        when(dRepository.save(any(Drone.class))).thenReturn(this.drone);
        lenient().when(mRepository.save(any(Medication.class))).thenReturn(med);
        try {
            dService.load(meds, 1l);
            assertEquals(drone.getMedications().size(), meds.size());
        } catch (IllegalArgumentException | ResourceNotFoundException | RequirementNotMetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldLoad_ThrowRequirementNotMetException() {
        drone.setBatteryCapacity(20);
        when(dRepository.findById(anyLong())).thenReturn(Optional.of(this.drone));
        Medication med = new Medication("paracetamol", 50, "ABCDEF_");
        List<Medication> meds = Collections.singletonList(med);
        assertThrows(RequirementNotMetException.class, () -> dService.load(meds, 1l),
                "Drone battery capacity is below 25%");
        drone.setBatteryCapacity(25);
        med.setWeight(150);
        assertThrows(RequirementNotMetException.class, () -> dService.load(meds, 1l),
                "Total medication weight is above drone model specification or Drone is not in Idle state");
    }

    @Test
    public void shouldFindById_Success() {
        when(dRepository.findById(anyLong())).thenReturn(Optional.of(drone));
        try {
            Drone foundDrone = dService.findById(1);
            assertEquals(foundDrone.getSerialNumber(), drone.getSerialNumber());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldUpdate_Success() {
        when(dRepository.findById(anyLong())).thenReturn(Optional.of(drone));
        when(dRepository.save(any(Drone.class))).thenReturn(drone);
        try {
            Drone savedDrone = dService.update(dto);
            assertEquals(drone.getBatteryCapacity(), savedDrone.getBatteryCapacity());
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetMedications_Success() {
        Medication med = new Medication("paracetamol", 50, "ABCDEF_");
        List<Medication> meds = Collections.singletonList(med);
        drone.setMedications(meds);
        when(dRepository.findById(anyLong())).thenReturn(Optional.of(drone));
        try {
            List<Medication> actualMeds;
            actualMeds = dService.getMedications(1l);
            assertEquals(meds.size(), actualMeds.size());

        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

}
