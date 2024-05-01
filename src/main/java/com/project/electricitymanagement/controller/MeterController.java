package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.service.MeterService;
import com.project.electricitymanagement.entity.Meter;
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
 * Controller class for handling HTTP requests related to meters.
 */
@RestController
@RequestMapping("/api/meters")
@Tag(name = "Meter Controller", description = "Endpoints for managing meters")
public class MeterController {
    /**
     * Defining the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MeterController.class);
    @Autowired
    private MeterService meterService;

    /**
     * Retrieves all meters.
     *
     * @return The Response entity with the list of meters.
     */
    @GetMapping
    @Operation(summary = "Retrieve all meters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of meters"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Meter>> getAllMeters() {
        LOGGER.info("Request for fetching all meters");

        List<Meter> meters = meterService.getAllMeters();
        LOGGER.info(String.format("Fetched %d meters", meters.size()));

        return new ResponseEntity<>(meters, HttpStatus.OK);
    }

    /**
     * Retrieves a meter by its id.
     *
     * @param id The id of the meter to retrieve.
     * @return The Response entity with the meter with the specified id.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a meter by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved meter"),
            @ApiResponse(responseCode = "404", description = "Meter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Meter> getMeterById(@PathVariable(value = "id")final Long id) {
        LOGGER.info(String.format("Request for meter with id %d", id));

        Meter meter = meterService.getMeterById(id);
        LOGGER.info(String.format("Fetched meter with id %d", id));

        return new ResponseEntity<>(meter, HttpStatus.OK);
    }
    /**
     * Creates a new Meter.
     *
     * @param meterDto The meterDto containing information about the new meter.
     * @return The Response entity with the newly created meter.
     */
    @PostMapping
    @Operation(summary = "Create a new meter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Meter created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Meter> createMeter(@Valid @RequestBody final MeterDto meterDto) {
        LOGGER.info(("Request for creating a new meter"));

        Meter createdMeter = meterService.createMeter(meterDto);
        LOGGER.info(String.format("New meter created with id %d", createdMeter.getId()));

        return new ResponseEntity<>(createdMeter, HttpStatus.CREATED);
    }
    /**
     * Updates an existing meter.
     *
     * @param id           The id of the meter to update
     * @param meterDetails The meterDto that contains the updated information of the meter.
     * @return The Response entity with the updated meter.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing meter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Meter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Meter> updateMeter(@PathVariable(value = "id")final  Long id,
                                             @Valid @RequestBody final MeterDto meterDetails) {
        LOGGER.info(String.format("Request for updating meter with id %d", id));

        Meter updatedMeter = meterService.updateMeter(id, meterDetails);
        LOGGER.info(String.format("Updated meter with id %d", id));

        return new ResponseEntity<>(updatedMeter, HttpStatus.OK);
    }
    /**
     * Deletes a meter by its id.
     *
     * @param id The id of the meter to be deleted.
     * @return Status of operation: 200 if successful, or 404 if meter is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a meter by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Meter deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Meter not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Object> deleteMeter(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request to delete meter with id %d", id));

        meterService.deleteMeter(id);
        LOGGER.info(String.format("Meter deleted with id %d", id));

        return ResponseEntity.ok().build();
    }
}

