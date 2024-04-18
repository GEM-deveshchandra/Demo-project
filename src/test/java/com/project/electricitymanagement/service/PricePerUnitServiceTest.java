package com.project.electricitymanagement.service;


import com.project.electricitymanagement.dto.PricePerUnitDto;
import com.project.electricitymanagement.entity.PricePerUnit;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PricePerUnitServiceTest {

    @Mock
    private PricePerUnitRepository pricePerUnitRepository;

    @InjectMocks
    private PricePerUnitService pricePerUnitService;

    private PricePerUnit testPricePerUnit;
    private PricePerUnitDto testPricePerUnitDto;

    @BeforeEach
    void setUp() {
        testPricePerUnit = new PricePerUnit();
        testPricePerUnit.setId(1L);
        testPricePerUnit.setUnitRangeLower(0);
        testPricePerUnit.setUnitRangeUpper(100);
        testPricePerUnit.setPrice(10.0);

        testPricePerUnitDto = new PricePerUnitDto();
        testPricePerUnitDto.setUnitRangeLower(0);
        testPricePerUnitDto.setUnitRangeUpper(100);
        testPricePerUnitDto.setPrice(10.0);
    }

    @Test
    void testGetAllPricePerUnit() {
        List<PricePerUnit> pricePerUnitList = new ArrayList<>();
        pricePerUnitList.add(testPricePerUnit);

        when(pricePerUnitRepository.findAll()).thenReturn(pricePerUnitList);

        List<PricePerUnit> result = pricePerUnitService.getAllPricePerUnit();

        assertEquals(1, result.size());
        assertEquals(testPricePerUnit, result.get(0));
    }

    @Test
    void testGetPricePerUnitById() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.of(testPricePerUnit));

        PricePerUnit result = pricePerUnitService.getPricePerUnitById(1L);

        assertEquals(testPricePerUnit, result);
    }

    @Test
    void testGetPricePerUnitById_ResourceNotFoundException() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pricePerUnitService.getPricePerUnitById(1L));
    }

    @Test
    void testCreatePricePerUnit() {
        when(pricePerUnitRepository.save(any(PricePerUnit.class))).thenReturn(testPricePerUnit);

        PricePerUnit result = pricePerUnitService.createPricePerUnit(testPricePerUnitDto);

        assertEquals(testPricePerUnit, result);
    }

    @Test
    void testUpdatePricePerUnit() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.of(testPricePerUnit));
        when(pricePerUnitRepository.save(any(PricePerUnit.class))).thenReturn(testPricePerUnit);

        PricePerUnit updatedPricePerUnit = new PricePerUnit();
        BeanUtils.copyProperties(testPricePerUnitDto, updatedPricePerUnit);
        updatedPricePerUnit.setId(1L);

        PricePerUnit result = pricePerUnitService.updatePricePerUnit(1L, testPricePerUnitDto);

        assertEquals(testPricePerUnit, result);
    }

    @Test
    void testUpdatePricePerUnit_ResourceNotFoundException() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pricePerUnitService.updatePricePerUnit(1L, testPricePerUnitDto));
    }

    @Test
    void testDeletePricePerUnit() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.of(testPricePerUnit));

        ResponseEntity<Object> result = pricePerUnitService.deletePricePerUnit(1L);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void testDeletePricePerUnit_ResourceNotFoundException() {
        when(pricePerUnitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> pricePerUnitService.deletePricePerUnit(1L));
    }
}