package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.SupplierDto;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling HTTP requests related to suppliers.
 */
@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier Controller", description = "Endpoints for managing suppliers")

public class SupplierController {
    /**
     * Defining the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);
    @Autowired
    private SupplierService supplierService;

    /**
     * Retrieves all suppliers.
     *
     * @return The Response entity with the list of suppliers.
     */
    @GetMapping
    @Operation(summary = "Retrieve all suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of suppliers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        LOGGER.info("Request for fetching all suppliers");
        List<Supplier> suppliers = supplierService.getAllSuppliers();
        LOGGER.info(String.format("Fetched %d suppliers", suppliers.size()));

        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    /**
     * Retrieves a supplier by its id.
     *
     * @param id The id of the supplier to retrieve.
     * @return The Response entity with the supplier with the specified id.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a supplier by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved supplier"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Supplier> getSupplierById(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request for supplier with id %d", id));

        Supplier supplier = supplierService.getSupplierById(id);
        LOGGER.info(String.format("Fetched supplier with id %d", id));
        return new ResponseEntity<>(supplier, HttpStatus.OK);
    }

    /**
     * Creates a new supplier.
     *
     * @param supplierDto The supplierDto containing information about the new supplier.
     * @return The Response entity with the newly created supplier.
     */
    @PostMapping
    @Operation(summary = "Create a new supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody final SupplierDto supplierDto) {
        LOGGER.info("Request for creating a new supplier");

        Supplier createdSupplier = supplierService.createSupplier(supplierDto);
        LOGGER.info(String.format("New supplier created with id %d", createdSupplier.getId()));

        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    /**
     * Updates an existing meter.
     *
     * @param id              The id of the supplier to update
     * @param supplierDetails The SupplierDto that contains the updated information of the supplier.
     * @return The Response entity with the updated supplier.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Supplier> updateSupplier(@PathVariable(value = "id")final  Long id,
                                                   @Valid @RequestBody final SupplierDto supplierDetails) {
        LOGGER.info(String.format("Request for updating supplier with id %d", id));

        Supplier updatedSupplier = supplierService.updateSupplier(id, supplierDetails);
        LOGGER.info(String.format("Updated supplier with id %d ", id));

        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    /**
     * Deletes a supplier by its id.
     *
     * @param id The id of the supplier to be deleted.
     * @return Status of operation: 200 if successful, or 404 if meter is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> deleteSupplier(@PathVariable(value = "id")final Long id) {
        LOGGER.info(String.format("Request to delete supplier with id %d", id));

        supplierService.deleteSupplier(id);
        LOGGER.info(String.format("Supplier deleted with id %d", id));

        return ResponseEntity.ok().build();
    }
}

