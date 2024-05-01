package com.project.electricitymanagement.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.electricitymanagement.dto.PricePerUnitDto;
import com.project.electricitymanagement.entity.PricePerUnit;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for PricePerUnit feature
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PricePerUnitIntegrationTest extends AbstractContainerTest {

    private static final String PRICE_PER_UNIT_API_URL = "/api/price-per-unit";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PricePerUnitRepository pricePerUnitRepository;

    @BeforeEach
    void resetDatabase() {
        pricePerUnitRepository.deleteAll();// Deletes all data from the price-per-unit table
    }

    @Test
     void testCreatePricePerUnit_ValidInput() throws Exception {
        PricePerUnitDto pricePerUnitDto = new PricePerUnitDto();
        pricePerUnitDto.setUnitRangeLower(0);
        pricePerUnitDto.setUnitRangeUpper(100);
        pricePerUnitDto.setPrice(3d);

        mockMvc.perform(post(PRICE_PER_UNIT_API_URL)
                        .content(objectMapper.writeValueAsString(pricePerUnitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.unitRangeLower", is(pricePerUnitDto.getUnitRangeLower())))
                .andExpect(jsonPath("$.unitRangeUpper", is(pricePerUnitDto.getUnitRangeUpper())))
                .andExpect(jsonPath("$.price", is(pricePerUnitDto.getPrice())));
    }

    @Test
     void testCreatePricePerUnit_InvalidInput() throws Exception {
        PricePerUnitDto pricePerUnitDto = new PricePerUnitDto();
        pricePerUnitDto.setUnitRangeUpper(100);

        mockMvc.perform(post(PRICE_PER_UNIT_API_URL)
                        .content(objectMapper.writeValueAsString(pricePerUnitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
     void testGetAllPricePerUnit() throws Exception {
        PricePerUnit pricePerUnit1 = new PricePerUnit();
        pricePerUnit1.setUnitRangeLower(0);
        pricePerUnit1.setUnitRangeUpper(100);
        pricePerUnit1.setPrice(3d);
        PricePerUnit pricePerUnit2 = new PricePerUnit();
        pricePerUnit2.setUnitRangeLower(101);
        pricePerUnit2.setUnitRangeUpper(500);
        pricePerUnit2.setPrice(5d);
        pricePerUnitRepository.saveAll(List.of(pricePerUnit1,pricePerUnit2));

        mockMvc.perform(get(PRICE_PER_UNIT_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].unitRangeLower", is(pricePerUnit1.getUnitRangeLower())))
                .andExpect(jsonPath("$[0].unitRangeUpper", is(pricePerUnit1.getUnitRangeUpper())))
                .andExpect(jsonPath("$[0].price", is(pricePerUnit1.getPrice())))
                .andExpect(jsonPath("$[1].unitRangeLower", is(pricePerUnit2.getUnitRangeLower())))
                .andExpect(jsonPath("$[1].unitRangeUpper", is(pricePerUnit2.getUnitRangeUpper())))
                .andExpect(jsonPath("$[1].price", is(pricePerUnit2.getPrice())));
    }

    @Test
     void testGetPricePerUnitById() throws Exception {

        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setUnitRangeLower(0);
        pricePerUnit.setUnitRangeUpper(100);
        pricePerUnit.setPrice(3d);
        PricePerUnit savedPricePerUnit = pricePerUnitRepository.save(pricePerUnit);

        mockMvc.perform(get(PRICE_PER_UNIT_API_URL + "/{id}", savedPricePerUnit.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedPricePerUnit.getId().intValue())))
                .andExpect(jsonPath("$.unitRangeLower", is(savedPricePerUnit.getUnitRangeLower())))
                .andExpect(jsonPath("$.unitRangeUpper", is(savedPricePerUnit.getUnitRangeUpper())))
                .andExpect(jsonPath("$.price", is(savedPricePerUnit.getPrice())));
    }

    @Test
     void testGetPricePerUnitById_NotFound() throws Exception {
        mockMvc.perform(get(PRICE_PER_UNIT_API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePricePerUnit() throws Exception {
        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setUnitRangeLower(0);
        pricePerUnit.setUnitRangeUpper(100);
        pricePerUnit.setPrice(3d);
        PricePerUnit savedPricePerUnit = pricePerUnitRepository.save(pricePerUnit);

        PricePerUnitDto updatedPricePerUnitDto = new PricePerUnitDto();
        updatedPricePerUnitDto.setUnitRangeLower(0);
        updatedPricePerUnitDto.setUnitRangeUpper(200);
        updatedPricePerUnitDto.setPrice(5d);

        mockMvc.perform(put(PRICE_PER_UNIT_API_URL + "/{id}", savedPricePerUnit.getId())
                        .content(objectMapper.writeValueAsString(updatedPricePerUnitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedPricePerUnit.getId().intValue())))
                .andExpect(jsonPath("$.unitRangeLower", is(updatedPricePerUnitDto.getUnitRangeLower())))
                .andExpect(jsonPath("$.unitRangeUpper", is(updatedPricePerUnitDto.getUnitRangeUpper())))
                .andExpect(jsonPath("$.price", is(updatedPricePerUnitDto.getPrice())));
    }

    @Test
     void testUpdatePricePerUnit_NotFound() throws Exception {
        PricePerUnitDto updatedPricePerUnitDto = new PricePerUnitDto();
        updatedPricePerUnitDto.setUnitRangeLower(0);
        updatedPricePerUnitDto.setUnitRangeUpper(200);
        updatedPricePerUnitDto.setPrice(5d);

        mockMvc.perform(put(PRICE_PER_UNIT_API_URL + "/{id}", 100)
                        .content(objectMapper.writeValueAsString(updatedPricePerUnitDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
     void testDeletePricePerUnit() throws Exception {
        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setUnitRangeLower(0);
        pricePerUnit.setUnitRangeUpper(100);
        pricePerUnit.setPrice(3d);
        PricePerUnit savedPricePerUnit = pricePerUnitRepository.save(pricePerUnit);

        mockMvc.perform(delete(PRICE_PER_UNIT_API_URL + "/{id}", savedPricePerUnit.getId()))
                .andExpect(status().isOk());

        assertFalse(pricePerUnitRepository.existsById(savedPricePerUnit.getId()));
    }

    @Test
     void testDeletePricePerUnit_NotFound() throws Exception {
        mockMvc.perform(delete(PRICE_PER_UNIT_API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }
}
