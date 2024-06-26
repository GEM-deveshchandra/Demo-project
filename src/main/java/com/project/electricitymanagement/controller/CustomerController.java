package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import com.project.electricitymanagement.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller class for handling HTTP requests related to customers.
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Controller", description = "Endpoints for managing customers")
public class CustomerController {
    /**
     * Defining the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerService customerService;

    /**
     * Retrieves all customers.
     *
     * @return The Response entity with the list of all customers.
     */

    @GetMapping
    @Operation(summary = "Retrieve all customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of customers"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Customer>> getAllCustomers() {
        LOGGER.info("Request for fetching all customers");
        List<Customer> customers = customerService.getAllCustomers();
        LOGGER.info(String.format("Fetched %d customers", customers.size()));
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    /**
     * Retrieves a customer by its id.
     *
     * @param id The id of the customer to retrieve.
     * @return The Response entity with the customer with the specified id.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved customer"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request for customer with id %d", id));
        Customer customer = customerService.getCustomerById(id);
        LOGGER.info(String.format("Fetched customer with id: %d", id));
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    /**
     * Creates a new customer.
     *
     * @param customerDto The customerDto containing information about the new customer.
     * @return The Response entity with the newly created customer.
     */
    @PostMapping
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody final CustomerDto customerDto) {
        LOGGER.info("Request for creating a new customer");
        Customer createdCustomer = customerService.createCustomer(customerDto);
        LOGGER.info(String.format("New customer created with id %d", createdCustomer.getId()));
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }
    /**
     * Updates an existing customer.
     *
     * @param id          The id of the customer to update
     * @param customerDetails The customerDto that contains the updated information of the customer.
     * @return The Response entity with the updated customer.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
    })
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") final Long id,
                                                   @Valid @RequestBody final CustomerDto customerDetails) {
        LOGGER.info(String.format("Request for updating customer with id %d", id));
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);

        LOGGER.info(String.format("Updated customer with id %d", id));
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
    /**
     * Retrieves the bill of the customer by its id.
     *
     * @param id The id of the customer
     * @return The Response entity with the bill amount of the given customer id.
     */
    @GetMapping("/{id}/bill")
    @Operation(summary = "Retrieve the bill amount of a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved bill amount"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Double> getBillById(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request for fetching bill of customer with id %d", id));

        Double billAmount = customerService.getCustomerBillById(id);
        LOGGER.info(String.format("Fetched bill of customer with id %d", id));

        return new ResponseEntity<>(billAmount, HttpStatus.OK);
    }
    /**
     * Deletes a customer by its id.
     *
     * @param id The id of the customer to be deleted.
     * @return Status of operation: 200 if successful, or 404 if customer is not found.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    public ResponseEntity<Object> deleteCustomerById(@PathVariable(value = "id") final Long id) {
        LOGGER.info(String.format("Request to delete customer with id %d", id));

        customerService.deleteCustomerById(id);
        LOGGER.info(String.format("Customer deleted with id %d", id));

        return ResponseEntity.ok().build();
    }
}
