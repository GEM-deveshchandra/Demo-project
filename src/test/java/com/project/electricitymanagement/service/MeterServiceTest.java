package com.project.electricitymanagement.service;

import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * Test class for Meter Service.
 */
@ExtendWith(MockitoExtension.class)
class MeterServiceTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterService meterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateMeter() {

        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(11);
        meterDto.setMinBillAmount(100.0);

        Meter savedMeter = new Meter();
        BeanUtils.copyProperties(meterDto, savedMeter);

        when(meterRepository.save(any(Meter.class))).thenReturn(savedMeter);
        Meter createdMeter = meterService.createMeter(meterDto);

        assertNotNull(createdMeter);
        assertEquals(meterDto.getLoadAmount(), createdMeter.getLoadAmount());
        assertEquals(meterDto.getMinBillAmount(), createdMeter.getMinBillAmount());
    }

    @Test
    void testGetAllMeters() {

        List<Meter> meters = new ArrayList<>();
        meters.add(new Meter());
        meters.add(new Meter());

        when(meterRepository.findAll()).thenReturn(meters);

        List<Meter> fetchedMeters = meterService.getAllMeters();

        assertNotNull(fetchedMeters);
        assertEquals(2, fetchedMeters.size());
    }

    @Test
    void testGetMeterById() {

        Long id = 1L;
        Meter meter = new Meter();
        meter.setId(id);

        when(meterRepository.findById(id)).thenReturn(Optional.of(meter));
        Meter fetchedMeter = meterService.getMeterById(id);

        assertNotNull(fetchedMeter);
        assertEquals(id, fetchedMeter.getId());
    }

    @Test
    void testGetMeterById_ResourceNotFoundException() {

        Long id = 1L;

        when(meterRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> meterService.getMeterById(id));
    }

    @Test
    void testUpdateMeter() {

        Long id = 1L;
        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(9);
        meterDto.setMinBillAmount(100.0);

        Meter existingMeter = new Meter();
        existingMeter.setId(id);

        Meter updatedMeter = new Meter();
        updatedMeter.setId(id);
        BeanUtils.copyProperties(meterDto, updatedMeter);

        when(meterRepository.findById(id)).thenReturn(Optional.of(existingMeter));
        when(meterRepository.save(any(Meter.class))).thenReturn(updatedMeter);


        Meter result = meterService.updateMeter(id, meterDto);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(meterDto.getLoadAmount(), result.getLoadAmount());
        assertEquals(meterDto.getMinBillAmount(), result.getMinBillAmount());
    }

    @Test
    void testUpdateMeter_ResourceNotFoundException() {

        Long id = 1L;
        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(7);
        meterDto.setMinBillAmount(100.0);

        when(meterRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> meterService.updateMeter(id, meterDto));
    }

    @Test
    void testDeleteMeter() {

        Long id = 1L;

        Meter existingMeter = new Meter();
        existingMeter.setId(id);

        when(meterRepository.findById(id)).thenReturn(Optional.of(existingMeter));

        ResponseEntity<Object> responseEntity = meterService.deleteMeter(id);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testDeleteMeter_ResourceNotFoundException() {

        Long id = 1L;
        when(meterRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> meterService.deleteMeter(id));
    }


}