package com.project.electricitymanagement.repository;

import com.project.electricitymanagement.entity.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for managing Meter entities.
 */
@Repository
public interface MeterRepository extends JpaRepository<Meter, Long> {
}
