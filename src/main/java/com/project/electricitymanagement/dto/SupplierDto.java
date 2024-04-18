package com.project.electricitymanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * The Supplier DTO class for data transfer.
 */
@Getter
@Setter
public class SupplierDto {
    /**
     * Name of the supplier.
     */
    @NotEmpty(message = "Supplier name can't be empty")
    private String name;
    /**
     * The type of Supplier - Urban/Rural.
     */
    private String supplierType;

}
