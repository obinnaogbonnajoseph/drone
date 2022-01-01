package com.obinnaogbonna.drone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.obinnaogbonna.drone.dao.CustomMedRepository;
import com.obinnaogbonna.drone.dao.MedicationRepository;
import com.obinnaogbonna.drone.dto.MedicationDto;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.entity.Model;
import com.obinnaogbonna.drone.serviceImpl.MedicationServiceImpl;
import com.obinnaogbonna.drone.utils.ModelEnum;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class MedicationServiceLayerTest {

    @Mock
    private CustomMedRepository cMedRepository;

    @Mock
    private MedicationRepository mRepository;

    @Mock
    private ModelMapper mapper;

    MedicationService mService;

    final Medication medication = new Medication();

    final MedicationDto dto = new MedicationDto();

    @BeforeEach
    void init() {
        mService = new MedicationServiceImpl(mapper, mRepository, cMedRepository);
        medication.setName("paracetamol");
        medication.setWeight(120);
        medication.setCode("ABCDEFGHIJK_");

        dto.setName("paracetamol");
        dto.setWeight(120);
        dto.setCode("ABCDEFGHIJK_");

        Drone drone = new Drone();
        drone.setModel(new Model(ModelEnum.CRUISER_WEIGHT));

        medication.setDrone(drone);
    }

    @Test
    public void shouldSave_Success() {
        when(mRepository.save(any(Medication.class))).thenReturn(medication);
        Medication savedMed = mService.save(dto);
        assertEquals(medication.getCode(), savedMed.getCode());
    }

    @Test
    public void shouldFindById_Success() {
        when(mRepository.findById(anyLong())).thenReturn(Optional.of(medication));
        try {
            Medication actualMed;
            actualMed = mService.findById(1l);
            assertEquals(medication.getCode(), actualMed.getCode());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCheckLoadedMedications_Success() {
        try {
            when(cMedRepository.checkLoadedMedications(anyLong())).thenReturn(medication.getDrone());
            Drone actualDrone = mService.checkLoadedMedications(1l);
            assertEquals(medication.getDrone().getModel().getValue(), actualDrone.getModel().getValue());
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }

}
