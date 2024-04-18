package com.project.electricitymanagement.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a Meter entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Meter {
    /**
     * Primary key for the Meter entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Load amount of the meter.
     */
    @NotNull
    private String loadAmount;
    /**
     * Minimum bill amount associated with the load.
     */
    @NotNull
    private double minBillAmount;

}
