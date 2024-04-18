package com.project.electricitymanagement.repository;

import com.project.electricitymanagement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Customer entities.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
