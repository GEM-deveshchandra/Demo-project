package com.project.electricitymanagement.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
/**
 * The Customer DTO class for data transfer.
 */
@Getter
@Setter

public class CustomerDto {
    /**
     * Name of the customer.
     */
    @NotEmpty(message = "Name can't be empty")
    private String name;
    /**
     * Address of the customer.
     */
    @NotEmpty(message = "Address can't be empty")
    private String address;
    /**
     * Date when the connection was installed.
     */
    private LocalDate connectionDate;
    /**
     * Last recorded reading of the meter.
     */
    @NotNull
    private double lastReading;
    /**
     * Current reading of the meter.
     */
    @NotNull
    private double currentReading;
    /**
     * Meter Id associated with the customer.
     */
    @NotNull(message = "Invalid meterID: meterID is NULL")
    private Long meterId;
    /**
     * Supplier Id associated with the customer.
     */
    @NotNull(message = "Invalid supplierID: supplierID is NULL")
    private Long supplierId;
}
