package com.project.electricitymanagement.service;


import com.project.electricitymanagement.config.Constants;
import com.project.electricitymanagement.dto.SupplierDto;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing operations related to supplier.
 */

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * Creates a new supplier.
     *
     * @param supplierDto The supplierDto containing information about the new supplier.
     * @return The newly created supplier.
     */
    public Supplier createSupplier(final SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierDto.getName());
        supplier.setSupplierType(supplierDto.getSupplierType());

        return supplierRepository.save(supplier);
    }

    /**
     * Retrieves list of all the suppliers in the database.
     *
     * @return List of all the suppliers.
     */
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }


    /**
     * Retrieves a Supplier by its id from the database.
     *
     * @param id The id of the supplier to retrieve.
     * @return The supplier with the specified id.
     * @throws ResourceNotFoundException if the supplier with the specified id is not found.
     */
    public Supplier getSupplierById(final Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.SUPPLIER, Constants.ID, id));
    }

    /**
     * Updates an existing customer.
     *
     * @param id              The id of the supplier to update
     * @param supplierDetails The SupplierDto that contains the updated information of the supplier.
     * @return The updated supplier
     * @throws ResourceNotFoundException if the supplier with the specified id is not found.
     */

    public Supplier updateSupplier(final Long id, final SupplierDto supplierDetails) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.SUPPLIER, Constants.ID, id));

        supplier.setName(supplierDetails.getName());
        supplier.setSupplierType(supplierDetails.getSupplierType());

        return supplierRepository.save(supplier);
    }

    /**
     * Deletes a supplier by its id.
     *
     * @param id The id of the supplier to be deleted.
     * @return Response entity with status of the operation.
     * @throws ResourceNotFoundException if the supplier with the specified id is not found.
     */

    public ResponseEntity<Object> deleteSupplier(final Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.SUPPLIER, Constants.ID, id));

        supplierRepository.delete(supplier);

        return ResponseEntity.ok().build();
    }


}
