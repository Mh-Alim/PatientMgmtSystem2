package com.mv.patient.service;

import com.mv.patient.dto.PatientRequestDTO;
import com.mv.patient.dto.PatientResponseDTO;
import com.mv.patient.exception.EmailAlreadyExistException;
import com.mv.patient.exception.PatientNotFoundException;
import com.mv.patient.grpc.BillingServiceGrpcClient;
import com.mv.patient.mapper.PatientMapper;
import com.mv.patient.model.Patient;
import com.mv.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    private BillingServiceGrpcClient billingServiceGrpcClient;
    public PatientService(PatientRepository patientRepository, BillingServiceGrpcClient billingServiceGrpcClient) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDTO).toList();
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistException("Email already exists " + patientRequestDTO.getEmail());
        }
        Patient patient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
        return PatientMapper.toDTO(patient);
    }

    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: "+id));

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
