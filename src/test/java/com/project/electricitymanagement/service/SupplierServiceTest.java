package com.project.electricitymanagement.service;


import com.project.electricitymanagement.dto.SupplierDto;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.SupplierRepository;
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
class SupplierServiceTest {

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierService supplierService;

    private Supplier testSupplier;
    private SupplierDto testSupplierDto;

    @BeforeEach
    void setUp() {
        testSupplier = new Supplier();
        testSupplier.setId(1L);
        testSupplier.setName("Danish");
        testSupplier.setSupplierType("Urban");

        testSupplierDto = new SupplierDto();
        testSupplierDto.setName("Danish");
        testSupplierDto.setSupplierType("Urban");
    }

    @Test
    void testCreateSupplier() {
        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);

        Supplier result = supplierService.createSupplier(testSupplierDto);

        assertEquals(testSupplier, result);
    }

    @Test
    void testGetAllSuppliers() {
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(testSupplier);

        when(supplierRepository.findAll()).thenReturn(supplierList);

        List<Supplier> result = supplierService.getAllSuppliers();

        assertEquals(1, result.size());
        assertEquals(testSupplier, result.get(0));
    }

    @Test
    void testGetSupplierById() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(testSupplier));

        Supplier result = supplierService.getSupplierById(1L);

        assertEquals(testSupplier, result);
    }

    @Test
    void testGetSupplierById_ResourceNotFoundException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> supplierService.getSupplierById(1L));
    }

    @Test
    void testUpdateSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(testSupplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(testSupplier);

        Supplier updatedSupplier = new Supplier();
        BeanUtils.copyProperties(testSupplierDto, updatedSupplier);
        updatedSupplier.setId(1L);

        Supplier result = supplierService.updateSupplier(1L, testSupplierDto);

        assertEquals(testSupplier, result);
    }

    @Test
    void testUpdateSupplier_ResourceNotFoundException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> supplierService.updateSupplier(1L, testSupplierDto));
    }

    @Test
    void testDeleteSupplier() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.of(testSupplier));

        ResponseEntity<Object> result = supplierService.deleteSupplier(1L);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void testDeleteSupplier_ResourceNotFoundException() {
        when(supplierRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> supplierService.deleteSupplier(1L));
    }
}