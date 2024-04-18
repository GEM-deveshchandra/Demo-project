package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.PricePerUnitDto;
import com.project.electricitymanagement.service.PricePerUnitService;
import com.project.electricitymanagement.entity.PricePerUnit;
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
 * Controller class for handling HTTP Requests related to PricePerUnit.
 */
@RestController
@RequestMapping("/api/price-per-unit")
public class PricePerUnitController {
    /**
     * Defining the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PricePerUnitController.class);
    @Autowired
    private PricePerUnitService pricePerUnitService;

    /**
     * Retrieves all Prices per unit.
     *
     * @return The Response entity with the list of all price per unit.
     */
    @GetMapping
    public ResponseEntity<List<PricePerUnit>> getAllPricePerUnit() {
        LOGGER.info("Request for fetching all prices per unit");
        List<PricePerUnit> pricePerUnits = pricePerUnitService.getAllPricePerUnit();
        LOGGER.info(String.format("Fetched %d prices per unit", pricePerUnits.size()));
        return new ResponseEntity<>(pricePerUnits, HttpStatus.OK);

    }
    /**
     * Retrieves a price per unit by its id.
     *
     * @param id The id of the price per unit to retrieve.
     * @return The Response entity with the price per unit as required.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PricePerUnit> getPricePerUnitById(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request for price per unit with id %d", id));
        PricePerUnit pricePerUnit = pricePerUnitService.getPricePerUnitById(id);
        LOGGER.info(String.format("Fetched price per unit with id: %d", id));
        return new ResponseEntity<>(pricePerUnit, HttpStatus.OK);

    }
    /**
     * Creates a new price per unit.
     *
     * @param pricePerUnitDto The pricePerUnitDto containing information about the new price per unit.
     * @return The Response entity with the newly created price per unit.
     */
    @PostMapping
    public ResponseEntity<PricePerUnit> createPricePerUnit(@Valid @RequestBody final PricePerUnitDto pricePerUnitDto) {
        LOGGER.info(("Request for creating a new price per unit."));

        PricePerUnit createdPricePerUnit = pricePerUnitService.createPricePerUnit(pricePerUnitDto);
        LOGGER.info(String.format("New price per unit created with id %d", createdPricePerUnit.getId()));

        return new ResponseEntity<>(createdPricePerUnit, HttpStatus.CREATED);
    }

    /**
     * Updates an existing price per unit.
     *
     * @param id           The id of the price per unit to update
     * @param pricePerUnitDetails The PricePerUnitDto that contains the updated information of the price per unit.
     * @return The Response entity with the updated price per unit.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PricePerUnit> updatePricePerUnit(@PathVariable(value = "id")final  Long id,
                                             @Valid @RequestBody final PricePerUnitDto pricePerUnitDetails) {
        LOGGER.info(String.format("Request for updating price per unit with id %d", id));

        PricePerUnit updatedPricePerUnit  = pricePerUnitService.updatePricePerUnit(id, pricePerUnitDetails);
        LOGGER.info(String.format("Updated price per unit with id %d", id));

        return new ResponseEntity<>(updatedPricePerUnit, HttpStatus.OK);
    }

    /**
     * Deletes a price per unit by its id.
     *
     * @param id The id of the price per unit to be deleted.
     * @return Status of operation: 200 if successful, or 404 if meter is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePricePerUnit(@PathVariable(value = "id") Long id) {
        LOGGER.info(String.format("Request to delete price per unit with id %d", id));

        pricePerUnitService.deletePricePerUnit(id);
        LOGGER.info(String.format("Price per unit deleted with id %d", id));

        return ResponseEntity.ok().build();
    }
}
