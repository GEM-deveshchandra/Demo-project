package com.project.electricitymanagement.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.electricitymanagement.dto.MeterDto;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.repository.MeterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MeterIntegrationTest extends AbstractContainerTest {

    private static final String API_URL = "/api/meters";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MeterRepository meterRepository;

    @BeforeEach
    void resetDatabase() {
        meterRepository.deleteAll();
    }

    @Test
    public void testCreateMeter_ValidInput() throws Exception {
        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(2);
        meterDto.setMinBillAmount(700D);

        mockMvc.perform(post(API_URL)
                        .content(objectMapper.writeValueAsString(meterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.loadAmount", is(meterDto.getLoadAmount())))
                .andExpect(jsonPath("$.minBillAmount", is(meterDto.getMinBillAmount())));
    }

    @Test
    public void testCreateMeter_InvalidInput() throws Exception {
        MeterDto meterDto = new MeterDto();
        meterDto.setLoadAmount(2);

        mockMvc.perform(post(API_URL)
                        .content(objectMapper.writeValueAsString(meterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllMeters() throws Exception {
        Meter meter1 = new Meter();
        meter1.setLoadAmount(1);
        meter1.setMinBillAmount(500D);

        Meter meter2 = new Meter();
        meter2.setLoadAmount(2);
        meter2.setMinBillAmount(700D);
        meterRepository.saveAll(List.of(meter1, meter2));

        mockMvc.perform(get(API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].loadAmount", is(meter1.getLoadAmount())))
                .andExpect(jsonPath("$[0].minBillAmount", is(meter1.getMinBillAmount())))
                .andExpect(jsonPath("$[1].loadAmount", is(meter2.getLoadAmount())))
                .andExpect(jsonPath("$[1].minBillAmount", is(meter2.getMinBillAmount())));
    }

    @Test
    public void testGetMeterById() throws Exception {
        Meter meter = new Meter();
        meter.setMinBillAmount(500D);
        meter.setLoadAmount(2);
        Meter savedMeter = meterRepository.save(meter);

        mockMvc.perform(get(API_URL + "/{id}", savedMeter.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedMeter.getId().intValue())))
                .andExpect(jsonPath("$.loadAmount", is(savedMeter.getLoadAmount())))
                .andExpect(jsonPath("$.minBillAmount", is(savedMeter.getMinBillAmount())));
    }

    @Test
    public void testGetMeterById_NotFound() throws Exception {
        mockMvc.perform(get(API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateMeter() throws Exception {
        Meter meter = new Meter();
        meter.setMinBillAmount(500D);
        meter.setLoadAmount(2);
        Meter savedMeter = meterRepository.save(meter);

        MeterDto updatedMeterDto = new MeterDto();
        updatedMeterDto.setLoadAmount(3);
        updatedMeterDto.setMinBillAmount(1000D);

        mockMvc.perform(put(API_URL + "/{id}", savedMeter.getId())
                        .content(objectMapper.writeValueAsString(updatedMeterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedMeter.getId().intValue())))
                .andExpect(jsonPath("$.loadAmount", is(updatedMeterDto.getLoadAmount())))
                .andExpect(jsonPath("$.minBillAmount", is(updatedMeterDto.getMinBillAmount())));
    }

    @Test
    public void testUpdateMeter_NotFound() throws Exception {
        MeterDto updatedMeterDto = new MeterDto();
        updatedMeterDto.setLoadAmount(3);
        updatedMeterDto.setMinBillAmount(1000D);

        mockMvc.perform(put(API_URL + "/{id}", 100)
                        .content(objectMapper.writeValueAsString(updatedMeterDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteMeter() throws Exception {
        Meter meter = new Meter();
        meter.setMinBillAmount(500D);
        meter.setLoadAmount(2);
        Meter savedMeter = meterRepository.save(meter);

        mockMvc.perform(delete(API_URL + "/{id}", savedMeter.getId()))
                .andExpect(status().isOk());

        assertFalse(meterRepository.existsById(savedMeter.getId()));
    }

    @Test
    public void testDeleteMeter_NotFound() throws Exception {
        mockMvc.perform(delete(API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }
}
