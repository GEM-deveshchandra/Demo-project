package com.project.electricitymanagement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Meter DTO class for data transfer.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeterDto {
    /**
     * Load amount on the meter.
     */
    @NotNull(message = "Load Amount is NULL")
    private int loadAmount;
    /**
     * Minimum bill amount corresponding to the load amount.
     */
    @NotNull(message = "Minimum Bill Amount is NULL")
    private double minBillAmount;
}
