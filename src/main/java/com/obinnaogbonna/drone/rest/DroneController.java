package com.obinnaogbonna.drone.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.obinnaogbonna.drone.dto.DroneDto;
import com.obinnaogbonna.drone.entity.Drone;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.service.DroneService;
import com.obinnaogbonna.drone.service.MedicationService;
import com.obinnaogbonna.drone.utils.RequirementNotMetException;
import com.obinnaogbonna.drone.utils.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/drone", produces = "application/json", consumes = "application/json")
public class DroneController {

    @Autowired
    private DroneService droneService;

    @Autowired
    private MedicationService medicationService;

    @PostMapping()
    public ResponseEntity<Drone> register(@NotNull @Valid @RequestBody DroneDto dto)
            throws IllegalArgumentException, RequirementNotMetException {
        return ResponseEntity.ok(this.droneService.register(dto));
    }

    @GetMapping("{id}")
    public ResponseEntity<Drone> findById(@PathVariable Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(this.droneService.findById(id));
    }

    @PutMapping()
    public ResponseEntity<Drone> update(@NotNull @Valid @RequestBody DroneDto dto)
            throws ResourceNotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(this.droneService.update(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) throws ResourceNotFoundException, IllegalArgumentException {
        this.droneService.delete(id);
        return ResponseEntity.ok("");
    }

    @GetMapping("load")
    public ResponseEntity<Drone> load(@RequestParam("medicationIds") List<Long> medicationIds,
            @RequestParam("droneId") Long droneId)
            throws ResourceNotFoundException, IllegalArgumentException, RequirementNotMetException {
        List<Medication> meds = new ArrayList<Medication>();
        Iterator<Long> itr = medicationIds.iterator();
        while (itr.hasNext()) {
            Medication med = this.medicationService.findById(itr.next());
            meds.add(med);
        }
        return ResponseEntity.ok(this.droneService.load(meds, droneId));
    }

    @GetMapping("fromLoadedMedications/{droneId}")
    public ResponseEntity<Drone> checkLoadedMedications(@PathVariable Long droneId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(this.medicationService.checkLoadedMedications(droneId));
    }

    @GetMapping("medications/{droneId}")
    public ResponseEntity<List<Medication>> getMedications(@PathVariable Long droneId)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(this.droneService.getMedications(droneId));
    }

    @GetMapping("available/{totalMedicationWeight}")
    public ResponseEntity<List<Drone>> findAvailable(@PathVariable("totalMedicationWeight") int totalWeight) {
        return ResponseEntity.ok(this.droneService.findAvailable(totalWeight));
    }

    @GetMapping("batteryLevel/{droneId}")
    public ResponseEntity<Integer> checkBatteryLevel(@PathVariable Long droneId)
            throws ResourceNotFoundException {
        Optional<Drone> oDrone = Optional.of(this.droneService.findById(droneId));
        return ResponseEntity.ok(oDrone.orElseThrow(() -> new ResourceNotFoundException("Drone could not be found"))
                .getBatteryCapacity());
    }
}
