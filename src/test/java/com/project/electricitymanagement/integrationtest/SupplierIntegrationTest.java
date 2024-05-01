package com.project.electricitymanagement.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.electricitymanagement.dto.SupplierDto;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SupplierIntegrationTest extends AbstractContainerTest {

    private static final String SUPPLIER_API_URL = "/api/suppliers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    void resetDatabase() {
        supplierRepository.deleteAll(); // Deletes all data from the supplier table
    }


    @Test
    public void testCreateSupplier() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setName("Mahesh");
        supplierDto.setSupplierType("Urban");

        mockMvc.perform(post(SUPPLIER_API_URL)
                        .content(objectMapper.writeValueAsString(supplierDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(supplierDto.getName())))
                .andExpect(jsonPath("$.supplierType", is(supplierDto.getSupplierType())));
    }
    @Test
    public void testCreateSupplier_InvalidInput() throws Exception {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setSupplierType("Urban");

        mockMvc.perform(post(SUPPLIER_API_URL)
                        .content(objectMapper.writeValueAsString(supplierDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testGetAllSuppliers() throws Exception {
        Supplier supplier1 = new Supplier();
        supplier1.setName("Ramesh");
        supplier1.setSupplierType("Urban");
        supplierRepository.save(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setName("Mahesh");
        supplier2.setSupplierType("Rural");
        supplierRepository.save(supplier2);

        mockMvc.perform(get(SUPPLIER_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(supplier1.getName())))
                .andExpect(jsonPath("$[0].supplierType", is(supplier1.getSupplierType())))
                .andExpect(jsonPath("$[1].name", is(supplier2.getName())))
                .andExpect(jsonPath("$[1].supplierType", is(supplier2.getSupplierType())));
    }

    @Test
    public void testGetSupplierById() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Mahesh");
        supplier.setSupplierType("Urban");
        Supplier savedSupplier = supplierRepository.save(supplier);

        mockMvc.perform(get(SUPPLIER_API_URL+"/{id}", savedSupplier.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedSupplier.getId().intValue())))
                .andExpect(jsonPath("$.name", is(savedSupplier.getName())))
                .andExpect(jsonPath("$.supplierType", is(savedSupplier.getSupplierType())));
    }
    @Test
    public void testGetSupplierById_NotFound() throws Exception {
        // Non-existing supplier
        mockMvc.perform(get(SUPPLIER_API_URL+"/{id}", 100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Mahesh");
        supplier.setSupplierType("Urban");
        Supplier savedSupplier = supplierRepository.save(supplier);

        SupplierDto updatedSupplierDto = new SupplierDto();
        updatedSupplierDto.setName("Updated Mahesh");
        updatedSupplierDto.setSupplierType("Rural");

        mockMvc.perform(put(SUPPLIER_API_URL+"/{id}", savedSupplier.getId())
                        .content(objectMapper.writeValueAsString(updatedSupplierDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedSupplier.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedSupplierDto.getName())))
                .andExpect(jsonPath("$.supplierType", is(updatedSupplierDto.getSupplierType())));
    }
    @Test
    public void testUpdateSupplier_NotFound() throws Exception {
        // Non-existing supplier
        SupplierDto updatedSupplierDto = new SupplierDto();
        updatedSupplierDto.setName("Mahesh");
        updatedSupplierDto.setSupplierType("Rural");

        mockMvc.perform(put(SUPPLIER_API_URL+"/{id}", 100)
                        .content(objectMapper.writeValueAsString(updatedSupplierDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testDeleteSupplier() throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Mahesh");
        supplier.setSupplierType("Urban");
        Supplier savedSupplier = supplierRepository.save(supplier);

        mockMvc.perform(delete(SUPPLIER_API_URL+"/{id}", savedSupplier.getId()))
                .andExpect(status().isOk());

        assertFalse(supplierRepository.existsById(savedSupplier.getId()));
    }
    @Test
    public void testDeleteSupplier_NotFound() throws Exception {
        // Non-existing supplier
        mockMvc.perform(delete(SUPPLIER_API_URL+"/{id}", 100))
                .andExpect(status().isNotFound());
    }
}
