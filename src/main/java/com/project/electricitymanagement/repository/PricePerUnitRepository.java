package com.project.electricitymanagement.repository;

import com.project.electricitymanagement.entity.PricePerUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing PricePerUnit entity.
 */

@Repository
public interface PricePerUnitRepository extends JpaRepository<PricePerUnit, Long> {
    /**
     * This method uses sql query to fetch the price per unit based on the units consumed by the customer.
     * @param unitConsumed  Unit consumed by the customer
     * @return fetches the Price based on the unit consumed
     */
    @Query(value = "Select price from price_per_unit where :unitConsumed between unit_range_lower and unit_range_upper;", nativeQuery = true)
     Optional<Double> findByUnitConsumed(@Param("unitConsumed") double unitConsumed);
}


