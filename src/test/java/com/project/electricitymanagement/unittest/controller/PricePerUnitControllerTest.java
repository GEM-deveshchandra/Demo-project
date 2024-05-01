package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.PricePerUnitDto;
import com.project.electricitymanagement.service.PricePerUnitService;
import com.project.electricitymanagement.entity.PricePerUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for PricePerUnitController.
 */
@ExtendWith(MockitoExtension.class)
public class PricePerUnitControllerTest {

    @Mock
    private PricePerUnitService pricePerUnitService;

    @InjectMocks
    private PricePerUnitController pricePerUnitController;

    @Test
    @DisplayName("Should return all prices per unit")
    void testGetAllPricePerUnit() {

        PricePerUnit pricePerUnit1 = new PricePerUnit();
        pricePerUnit1.setId(1L);
        pricePerUnit1.setUnitRangeLower(0);
        pricePerUnit1.setUnitRangeUpper(100);
        pricePerUnit1.setPrice(3.0);

        PricePerUnit pricePerUnit2 = new PricePerUnit();
        pricePerUnit2.setId(2L);
        pricePerUnit2.setUnitRangeLower(101);
        pricePerUnit2.setUnitRangeUpper(200);
        pricePerUnit2.setPrice(5.0);

        when(pricePerUnitService.getAllPricePerUnit()).thenReturn(Arrays.asList(pricePerUnit1, pricePerUnit2));

        ResponseEntity<List<PricePerUnit>> responseEntity = pricePerUnitController.getAllPricePerUnit();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<PricePerUnit> pricePerUnits = responseEntity.getBody();
        assertNotNull(pricePerUnits);
        assertEquals(2, pricePerUnits.size());
    }

    @Test
    @DisplayName("Should return price per unit by ID")
    void testGetPricePerUnitById() {
        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setId(1L);
        pricePerUnit.setUnitRangeLower(0);
        pricePerUnit.setUnitRangeUpper(100);
        pricePerUnit.setPrice(3.0);

        when(pricePerUnitService.getPricePerUnitById(anyLong())).thenReturn(pricePerUnit);

        ResponseEntity<PricePerUnit> responseEntity = pricePerUnitController.getPricePerUnitById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PricePerUnit fetchedPricePerUnit = responseEntity.getBody();
        assertNotNull(fetchedPricePerUnit);
        assertEquals(1L, fetchedPricePerUnit.getId());
        assertEquals(0, fetchedPricePerUnit.getUnitRangeLower());
        assertEquals(100, fetchedPricePerUnit.getUnitRangeUpper());
        assertEquals(3.0, fetchedPricePerUnit.getPrice());
    }

    @Test
    @DisplayName("Should create a new price per unit")
    void testCreatePricePerUnit() {
        // Arrange
        PricePerUnitDto pricePerUnitDto = new PricePerUnitDto();
        pricePerUnitDto.setUnitRangeLower(201);
        pricePerUnitDto.setUnitRangeUpper(300);
        pricePerUnitDto.setPrice(6.0);

        PricePerUnit createdPricePerUnit = new PricePerUnit();
        createdPricePerUnit.setId(3L);
        createdPricePerUnit.setUnitRangeLower(201);
        createdPricePerUnit.setUnitRangeUpper(300);
        createdPricePerUnit.setPrice(6.0);

        when(pricePerUnitService.createPricePerUnit(any(PricePerUnitDto.class))).thenReturn(createdPricePerUnit);


        ResponseEntity<PricePerUnit> responseEntity = pricePerUnitController.createPricePerUnit(pricePerUnitDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        PricePerUnit returnedPricePerUnit = responseEntity.getBody();
        assertNotNull(returnedPricePerUnit);
        assertEquals(3L, returnedPricePerUnit.getId());
        assertEquals(201, returnedPricePerUnit.getUnitRangeLower());
        assertEquals(300, returnedPricePerUnit.getUnitRangeUpper());
        assertEquals(6.0, returnedPricePerUnit.getPrice());
    }

    @Test
    @DisplayName("Should update an existing price per unit")
    void testUpdatePricePerUnit() {

        PricePerUnitDto pricePerUnitDto = new PricePerUnitDto();
        pricePerUnitDto.setUnitRangeLower(301);
        pricePerUnitDto.setUnitRangeUpper(400);
        pricePerUnitDto.setPrice(7.0);

        PricePerUnit updatedPricePerUnit = new PricePerUnit();
        updatedPricePerUnit.setId(1L);
        updatedPricePerUnit.setUnitRangeLower(301);
        updatedPricePerUnit.setUnitRangeUpper(400);
        updatedPricePerUnit.setPrice(7.0);

        when(pricePerUnitService.updatePricePerUnit(anyLong(), any(PricePerUnitDto.class))).thenReturn(updatedPricePerUnit);

        ResponseEntity<PricePerUnit> responseEntity = pricePerUnitController.updatePricePerUnit(1L, pricePerUnitDto);


        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        PricePerUnit returnedPricePerUnit = responseEntity.getBody();
        assertNotNull(returnedPricePerUnit);
        assertEquals(1L, returnedPricePerUnit.getId());
        assertEquals(301, returnedPricePerUnit.getUnitRangeLower());
        assertEquals(400, returnedPricePerUnit.getUnitRangeUpper());
        assertEquals(7.0, returnedPricePerUnit.getPrice());
    }

    @Test
    @DisplayName("Should delete a price per unit")
    void testDeletePricePerUnit() {
        ResponseEntity<Object> responseEntity = pricePerUnitController.deletePricePerUnit(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(pricePerUnitService, times(1)).deletePricePerUnit(anyLong());
    }
}