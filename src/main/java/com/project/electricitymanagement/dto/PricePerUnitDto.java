package com.project.electricitymanagement.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * The PricePerUni DTO class for data transfer.
 */
@Getter
@Setter
public class PricePerUnitDto {
    /**
     * Lower bound of the unit range.
     */
    @NotNull
    private int unitRangeLower;
    /**
     * Upper bound of the unit range.
     */
    @NotNull
    private int unitRangeUpper;
    /**
     * Price per unit corresponding to the range.
     */
    @NotNull
    private double price;

}
