package com.project.electricitymanagement.unittest.repository;

import com.project.electricitymanagement.entity.PricePerUnit;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test Class for PricePerUnit Repository.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PricePerUnitRepositoryTest {
    @Autowired
    private PricePerUnitRepository pricePerUnitRepository;
    private PricePerUnit pricePerUnit;

    @BeforeEach
    void setUp() {
        //this is required if data.sql file is there else we can comment this out.
        pricePerUnitRepository.deleteAll();
        pricePerUnit = new PricePerUnit(1L, 200, 300, 7d);
        pricePerUnitRepository.save(pricePerUnit);


    }

    @AfterEach
    void tearDown() {
        pricePerUnitRepository.deleteAll();

    }

    @Test
    void testFindByUnitConsumed_Found() {

        Optional<Double> priceByUnitConsumed = pricePerUnitRepository.findByUnitConsumed(201);
        assertThat(priceByUnitConsumed).isNotEmpty();
        assertThat(priceByUnitConsumed).isEqualTo(Optional.of(pricePerUnit.getPrice()));
    }

    @Test
    void testFindByUnitConsumed_NotFound() {
        Optional<Double> priceByUnitConsumed = pricePerUnitRepository.findByUnitConsumed(995809);
        assertThat(priceByUnitConsumed).isEmpty();

    }
}