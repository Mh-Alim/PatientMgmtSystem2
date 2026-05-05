package com.mv.patient.controller;


import com.mv.patient.dto.PatientRequestDTO;
import com.mv.patient.dto.PatientResponseDTO;
import com.mv.patient.dto.validators.CreatePatientValidationGroup;
import com.mv.patient.service.PatientService;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/patients")
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {
        List<PatientResponseDTO> patientResponseDTOS = patientService.getPatients();
        return ResponseEntity.ok().body(patientResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class }) @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable UUID id,@Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO) {
        log.info("[UPDATE_PATIENT] Id: {} and RequestDTO: {}", id, patientRequestDTO);
        PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, patientRequestDTO);
        return ResponseEntity.ok().body(patientResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        log.info("[DELETE_PATIENT] ID: {}", id);
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
