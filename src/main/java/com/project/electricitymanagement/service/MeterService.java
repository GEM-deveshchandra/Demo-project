package com.project.electricitymanagement.service;

import com.project.electricitymanagement.config.Constants;
import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.MeterRepository;
import com.project.electricitymanagement.entity.Meter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing operations related to Meter.
 */
@Service
public class MeterService {
    @Autowired
    private MeterRepository meterRepository;

    /**
     * Creates a new Meter.
     *
     * @param meterDto The meter containing information about the new meter.
     * @return The newly created meter.
     */
    public Meter createMeter(final MeterDto meterDto) {
        Meter meter = new Meter();
        BeanUtils.copyProperties(meterDto, meter);
        return meterRepository.save(meter);
    }

    /**
     * Retrieves list of all the meters in the database.
     *
     * @return List of all the meters
     */
    public List<Meter> getAllMeters() {
        return meterRepository.findAll();
    }

    /**
     * Retrieves a meter by its id from the database.
     *
     * @param id The id of the meter to retrieve.
     * @return The meter with the specified id.
     * @throws ResourceNotFoundException if the meter with the specified id is not found.
     */
    public Meter getMeterById(final Long id) {
        return meterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.METER, Constants.ID, id));
    }

    /**
     * Updates an existing meter.
     *
     * @param id           The id of the meter to update
     * @param meterDetails The meterDto that contains the updated information of the meter.
     * @return The updated meter
     * @throws ResourceNotFoundException if the meter with the specified id is not found.
     */
    public Meter updateMeter(final Long id, final MeterDto meterDetails) {
        Meter meter = meterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.METER, Constants.ID, id));

        BeanUtils.copyProperties(meterDetails, meter);

        return meterRepository.save(meter);
    }

    /**
     * Deletes a meter by its id.
     *
     * @param id The id of the meter to be deleted.
     * @return Response entity with status of the operation.
     * @throws ResourceNotFoundException if the meter with the specified id is not found.
     */
    public ResponseEntity<Object> deleteMeter(final Long id) {
        Meter meter = meterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.METER, Constants.ID, id));

        meterRepository.delete(meter);

        return ResponseEntity.ok().build();
    }


}
