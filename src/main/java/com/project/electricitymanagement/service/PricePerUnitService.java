package com.project.electricitymanagement.service;


import com.project.electricitymanagement.config.Constants;
import com.project.electricitymanagement.dto.PricePerUnitDto;
import com.project.electricitymanagement.entity.PricePerUnit;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing operations related to PricePerUnit.
 */

@Service
public class PricePerUnitService {

    @Autowired
    private
    PricePerUnitRepository pricePerUnitRepository;

    /**
     * Retrieves list of all the prices per unit in the database.
     *
     * @return List of all the prices per unit
     */
    public List<PricePerUnit> getAllPricePerUnit() {
        return pricePerUnitRepository.findAll();
    }

    /**
     * Retrieves a price per unit by its id from the database.
     *
     * @param id The id of the price per unit to retrieve.
     * @return The price per unit with the specified id.
     * @throws ResourceNotFoundException if the price per unit with the specified id is not found.
     */
    public PricePerUnit getPricePerUnitById(final Long id) {
        return pricePerUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PRICE_PER_UNIT, Constants.ID, id));
    }
    /**
     * Creates a new price per unit.
     *
     * @param pricePerUnitDto The price per unit containing information about the new price per unit.
     * @return The newly created price per unit.
     */
    public PricePerUnit createPricePerUnit(final PricePerUnitDto pricePerUnitDto) {
        PricePerUnit pricePerUnit = new PricePerUnit();
        BeanUtils.copyProperties(pricePerUnitDto, pricePerUnit);
        return pricePerUnitRepository.save(pricePerUnit);
    }

    /**
     * Updates an existing price per unit.
     *
     * @param id           The id of the price per unit to update
     * @param pricePerUnitDetails The PricePerUnitDto that contains the updated information of the price per unit.
     * @return The updated price per unit
     * @throws ResourceNotFoundException if the price per unit with the specified id is not found.
     */
    public PricePerUnit updatePricePerUnit(final Long id, final PricePerUnitDto pricePerUnitDetails) {
        PricePerUnit pricePerUnit = pricePerUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PRICE_PER_UNIT, Constants.ID, id));

        BeanUtils.copyProperties(pricePerUnitDetails, pricePerUnit);

        return pricePerUnitRepository.save(pricePerUnit);
    }

    /**
     * Deletes a price per unit by its id.
     *
     * @param id The id of the price per unit to be deleted.
     * @return Response entity with status of the operation.
     * @throws ResourceNotFoundException if the price per unit with the specified id is not found.
     */
    public ResponseEntity<Object> deletePricePerUnit(final Long id) {
        PricePerUnit pricePerUnit  = pricePerUnitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.PRICE_PER_UNIT, Constants.ID, id));

        pricePerUnitRepository.delete(pricePerUnit);

        return ResponseEntity.ok().build();
    }
}
