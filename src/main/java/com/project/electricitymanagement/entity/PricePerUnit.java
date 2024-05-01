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
 * Represents a PricePerUnit entity.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PricePerUnit {
    /**
     * Primary key for the PricePerUnit entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Lower bound of the unit range.
     */
    @NotNull
    private Integer unitRangeLower;
    /**
     * Upper bound of the unit range.
     */
    @NotNull
    private Integer unitRangeUpper;
    /**
     * Price per unit associated with the unit range.
     */
    @NotNull
    private Double price;

}

