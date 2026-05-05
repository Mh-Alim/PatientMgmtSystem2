package com.mv.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

// this entity is getting represented as tables in db

@Getter
@Setter
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private LocalDate registeredDate;

    @NotNull
    private String address;
}
