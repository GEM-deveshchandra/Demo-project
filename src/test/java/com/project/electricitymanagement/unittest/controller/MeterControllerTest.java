package com.project.electricitymanagement.unittest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import com.project.electricitymanagement.controller.MeterController;
import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.service.MeterService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for MeterController.
 */
@ExtendWith(MockitoExtension.class)
public class MeterControllerTest {

    @Mock
    private MeterService meterService;

    @InjectMocks
    private MeterController meterController;

    @Test
    @DisplayName("Should return all meters")
    void testGetAllMeters() {

        Meter meter1 = new Meter();
        meter1.setId(1L);
        meter1.setLoadAmount(6);

        Meter meter2 = new Meter();
        meter2.setId(2L);
        meter2.setLoadAmount(5);

        when(meterService.getAllMeters()).thenReturn(Arrays.asList(meter1, meter2));

        ResponseEntity<List<Meter>> responseEntity = meterController.getAllMeters();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Meter> meters = responseEntity.getBody();
        assertNotNull(meters);
        assertEquals(2, meters.size());
    }

    @Test
    @DisplayName("Should return meter by ID")
    void testGetMeterById() {
        Meter meter = new Meter();
        meter.setId(1L);
        meter.setLoadAmount(4);

        when(meterService.getMeterById(anyLong())).thenReturn(meter);

        ResponseEntity<Meter> responseEntity = meterController.getMeterById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Meter fetchedMeter = responseEntity.getBody();
        assertNotNull(fetchedMeter);
        assertEquals(1L, fetchedMeter.getId());
        assertEquals(4, fetchedMeter.getLoadAmount());
    }

    @Test
    @DisplayName("Should create a new meter")
    void testCreateMeter() {

        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(7);

        Meter createdMeter = new Meter();
        createdMeter.setId(1L);
        createdMeter.setLoadAmount(7);

        when(meterService.createMeter(any(MeterDto.class))).thenReturn(createdMeter);

        ResponseEntity<Meter> responseEntity = meterController.createMeter(meterDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Meter returnedMeter = responseEntity.getBody();
        assertNotNull(returnedMeter);
        assertEquals(1L, returnedMeter.getId());
        assertEquals(7, returnedMeter.getLoadAmount());
    }

    @Test
    @DisplayName("Should update an existing meter")
    void testUpdateMeter() {

        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(9);

        Meter updatedMeter = new Meter();
        updatedMeter.setId(1L);
        updatedMeter.setLoadAmount(30);

        when(meterService.updateMeter(anyLong(), any(MeterDto.class))).thenReturn(updatedMeter);

        ResponseEntity<Meter> responseEntity = meterController.updateMeter(1L, meterDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Meter returnedMeter = responseEntity.getBody();
        assertNotNull(returnedMeter);
        assertEquals(1L, returnedMeter.getId());
        assertEquals(30, returnedMeter.getLoadAmount());
    }

    @Test
    @DisplayName("Should delete a meter")
    void testDeleteMeter() {

        ResponseEntity<Object> responseEntity = meterController.deleteMeter(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(meterService, times(1)).deleteMeter(anyLong());
    }
}