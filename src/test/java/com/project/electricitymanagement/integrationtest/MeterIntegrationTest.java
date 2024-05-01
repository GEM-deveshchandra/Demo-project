package com.project.electricitymanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.repository.MeterRepository;
import com.project.electricitymanagement.service.MeterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MeterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeterRepository meterRepository;

    @BeforeEach
    public void setup() {
        // Initialize database with test data
        meterRepository.saveAll(List.of(
                new Meter(1L, 1, 500),
                new Meter(2L, 2, 700)
        ));
    }

    @Test
    public void testGetAllMeters() throws Exception {
        mockMvc.perform(get("/meters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].loadAmount", is(1)))
                .andExpect(jsonPath("$[0].minBillAmount", is(500)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].loadAmount", is(2)))
                .andExpect(jsonPath("$[1].minBillAmount", is(700)));
    }

    @Test
    public void testGetMeterById() throws Exception {
        mockMvc.perform(get("/meters/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.loadAmount", is(1)))
                .andExpect(jsonPath("$.minBillAmount", is(500)));
    }

    @Test
    public void testCreateMeter() throws Exception {
        MeterDto meterDto = new MeterDto(3, 800);

        mockMvc.perform(post("/meters")
                        .content(objectMapper.writeValueAsString(meterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.loadAmount", is(3)))
                .andExpect(jsonPath("$.minBillAmount", is(800)));
    }

    @Test
    public void testUpdateMeter() throws Exception {
        MeterDto meterDto = new MeterDto(1, 1000); // Update the load amount

        mockMvc.perform(put("/meters/{id}", 1)
                        .content(objectMapper.writeValueAsString(meterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.loadAmount", is(1))) // Load amount should remain unchanged
                .andExpect(jsonPath("$.minBillAmount", is(1000)));
    }

    @Test
    public void testDeleteMeter() throws Exception {
        mockMvc.perform(delete("/meters/{id}", 1))
                .andExpect(status().isOk());

        // Check if the meter is deleted
        mockMvc.perform(get("/meters/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
