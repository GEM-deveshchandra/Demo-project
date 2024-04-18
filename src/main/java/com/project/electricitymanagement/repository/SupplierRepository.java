package com.project.electricitymanagement.repository;

import com.project.electricitymanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Supplier entities.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
