package com.mv.patient.dto;

import com.mv.patient.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PatientRequestDTO {

    @NotBlank
    @Size(max = 100, message = "Name can't exceed more than 100 characters")
    private String name;

    @NotBlank
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "date of birth is required")
    private String dateOfBirth;

    @NotBlank(groups = {CreatePatientValidationGroup.class}, message = "registered date is required")
    private String registeredDate;
}
