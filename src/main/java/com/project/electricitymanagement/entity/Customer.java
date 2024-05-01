package com.project.electricitymanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;


/**
 * Represents a Customer entity.
 *
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    /**
     * Primary key for the Customer entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Name of the customer.
     */
    @NotNull
    private String name;
    /**
     * Address of the customer.
     */
    @NotNull
    private String address;
    /**
     * Date of establishing the connection.
     */
    @NotNull
    private LocalDate connectionDate;
    /**
     * Last recorded reading on the meter.
     */
    @NotNull
    private Double lastReading;
    /**
     * Current reading on the meter.
     */
    @NotNull
    private Double currentReading;
    /**
     * The total bill of the customer based on units consumed and the meter ID.
     */
    private Double billAmount;

    /**
     * The meter associated with the customer.
     */
    @NotNull
    @ManyToOne
    private Meter meter = new Meter();
    /**
     * The supplier associated with the customer.
     */
    @NotNull
    @ManyToOne
    private Supplier supplier = new Supplier();


}

