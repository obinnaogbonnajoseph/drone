package com.obinnaogbonna.drone.rest;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.obinnaogbonna.drone.dto.MedicationDto;
import com.obinnaogbonna.drone.entity.Medication;
import com.obinnaogbonna.drone.service.ImageService;
import com.obinnaogbonna.drone.service.MedicationService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/med", produces = "application/json", consumes = "application/json")
public class MedicationController {

    @Autowired
    private MedicationService mService;

    @Autowired
    private ImageService imageService;

    @PostMapping()
    public ResponseEntity<Medication> register(@NotNull @Valid @RequestBody MedicationDto dto)
            throws IllegalArgumentException {
        Medication med = this.mService.save(dto);
        return ResponseEntity.ok(med);
    }

    @PostMapping(value = "upload/{id}", consumes = { "multipart/form-data" })
    public ResponseEntity<Medication> imageUpload(@PathVariable Long id,
            @RequestParam("image") Optional<MultipartFile> imageFile)
            throws ResourceNotFoundException, IOException {
        byte[] imageData = this.imageService.processImage(imageFile.get());
        return ResponseEntity.ok(this.mService.imageUpdate(id, imageData));
    }

    @GetMapping("{id}")
    public ResponseEntity<Medication> findById(@PathVariable Long id)
            throws ResourceNotFoundException, IOException, DataFormatException {
        Medication med = this.mService.findById(id);
        if (med.getImage() != null) {
            byte[] imageData = this.imageService.decompress(med.getImage());
            med.setImage(imageData);
        }
        return ResponseEntity.ok(med);
    }

    @PutMapping()
    public ResponseEntity<Medication> update(@NotNull @Valid @RequestBody MedicationDto dto)
            throws ResourceNotFoundException, IllegalArgumentException {
        return ResponseEntity.ok(this.mService.update(dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable long id) throws ResourceNotFoundException, IllegalArgumentException {
        this.mService.delete(id);
        return ResponseEntity.ok("");
    }

}
