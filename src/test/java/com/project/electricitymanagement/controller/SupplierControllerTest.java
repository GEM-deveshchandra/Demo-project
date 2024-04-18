package com.project.electricitymanagement.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.project.electricitymanagement.dto.SupplierDto;
import com.project.electricitymanagement.service.SupplierService;
import com.project.electricitymanagement.entity.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Test class for SupplierController.
 */
@ExtendWith(MockitoExtension.class)
public class SupplierControllerTest {

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @Test
    @DisplayName("Should return all suppliers")
    void testGetAllSuppliers() {
        Supplier supplier1 = new Supplier();
        supplier1.setId(1L);
        supplier1.setName("Mahesh");
        supplier1.setSupplierType("Urban");

        Supplier supplier2 = new Supplier();
        supplier2.setId(2L);
        supplier2.setName("danish");
        supplier2.setSupplierType("Rural");

        when(supplierService.getAllSuppliers()).thenReturn(Arrays.asList(supplier1, supplier2));

        ResponseEntity<List<Supplier>> responseEntity = supplierController.getAllSuppliers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Supplier> suppliers = responseEntity.getBody();
        assertNotNull(suppliers);
        assertEquals(2, suppliers.size());
    }

    @Test
    @DisplayName("Should return supplier by ID")
    void testGetSupplierById() {

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Mahesh");
        supplier.setSupplierType("Urban");

        when(supplierService.getSupplierById(anyLong())).thenReturn(supplier);


        ResponseEntity<Supplier> responseEntity = supplierController.getSupplierById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Supplier fetchedSupplier = responseEntity.getBody();
        assertNotNull(fetchedSupplier);
        assertEquals(1L, fetchedSupplier.getId());
        assertEquals("Mahesh", fetchedSupplier.getName());
        assertEquals("Urban", fetchedSupplier.getSupplierType());
    }

    @Test
    @DisplayName("Should create a new supplier")
    void testCreateSupplier() {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setName("Mahesh");
        supplierDto.setSupplierType("Rural");

        Supplier createdSupplier = new Supplier();
        createdSupplier.setId(3L);
        createdSupplier.setName("Mahesh");
        createdSupplier.setSupplierType("Rural");

        when(supplierService.createSupplier(any(SupplierDto.class))).thenReturn(createdSupplier);

        ResponseEntity<Supplier> responseEntity = supplierController.createSupplier(supplierDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Supplier returnedSupplier = responseEntity.getBody();
        assertNotNull(returnedSupplier);
        assertEquals(3L, returnedSupplier.getId());
        assertEquals("Mahesh", returnedSupplier.getName());
        assertEquals("Rural", returnedSupplier.getSupplierType());
    }

    @Test
    @DisplayName("Should update an existing supplier")
    void shouldUpdateSupplier() {
        // Arrange
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setName("Updated Mahesh");
        supplierDto.setSupplierType("Urban");

        Supplier updatedSupplier = new Supplier();
        updatedSupplier.setId(1L);
        updatedSupplier.setName("Updated Mahesh");
        updatedSupplier.setSupplierType("Urban");

        when(supplierService.updateSupplier(anyLong(), any(SupplierDto.class))).thenReturn(updatedSupplier);

        // Act
        ResponseEntity<Supplier> responseEntity = supplierController.updateSupplier(1L, supplierDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Supplier returnedSupplier = responseEntity.getBody();
        assertNotNull(returnedSupplier);
        assertEquals(1L, returnedSupplier.getId());
        assertEquals("Updated Mahesh", returnedSupplier.getName());
        assertEquals("Urban", returnedSupplier.getSupplierType());
    }

    @Test
    @DisplayName("Should delete a supplier")
    void shouldDeleteSupplier() {

        ResponseEntity<Object> responseEntity = supplierController.deleteSupplier(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(supplierService, times(1)).deleteSupplier(anyLong());
    }
}