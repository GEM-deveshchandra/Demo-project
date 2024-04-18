package com.project.electricitymanagement.controller;

import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import com.project.electricitymanagement.service.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for CustomerController.
 */
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Test
    @DisplayName("Should return all customers")
    void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setId(3L);
        customer1.setName("Ramesh");

        Customer customer2 = new Customer();
        customer2.setId(10L);
        customer2.setName("Suresh");

        when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));


        ResponseEntity<List<Customer>> responseEntity = customerController.getAllCustomers();

      //First we check for the HttpStatus, then whether the response body is null or not and then whether the response is as expected or not.
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<Customer> customers = responseEntity.getBody();
        assertNotNull(customers);
        assertEquals(2, customers.size());
    }

    @Test
    @DisplayName("Should return customer by Id")
    void testGetCustomerById() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Ramesh");

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);


        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer fetchedCustomer = responseEntity.getBody();
        assertNotNull(fetchedCustomer);
        assertEquals(1L, fetchedCustomer.getId());
        assertEquals("Ramesh", fetchedCustomer.getName());
    }

    @Test
    @DisplayName("Should create a customer")
    void testCreateCustomer() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Rahul");

        Customer createdCustomer = new Customer();
        createdCustomer.setId(1L);
        createdCustomer.setName("Rahul");

        when(customerService.createCustomer(any(CustomerDto.class))).thenReturn(createdCustomer);


        ResponseEntity<Customer> responseEntity = customerController.createCustomer(customerDto);


        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Customer returnedCustomer = responseEntity.getBody();
        //first checking if the customer is created or not
        assertNotNull(returnedCustomer);
        assertEquals(1L, returnedCustomer.getId());
        assertEquals("Rahul", returnedCustomer.getName());
    }

    @Test
    @DisplayName("Should update an existing customer")
    void testUpdateCustomer() {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Updated Rahul");

        Customer updatedCustomer = new Customer();
        updatedCustomer.setId(1L);
        updatedCustomer.setName("Updated Rahul");

        when(customerService.updateCustomer(anyLong(), any(CustomerDto.class))).thenReturn(updatedCustomer);

        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(1L, customerDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Customer returnedCustomer = responseEntity.getBody();
        assertNotNull(returnedCustomer);
        assertEquals(1L, returnedCustomer.getId());
        assertEquals("Updated Rahul", returnedCustomer.getName());
    }

    @Test
    @DisplayName("Should return bill by id")
    void testGetBillById() {

        when(customerService.getCustomerBillById(anyLong())).thenReturn(100.0);

        ResponseEntity<Double> responseEntity = customerController.getBillById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(100.0, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should delete an existing customer")
    void testDeleteCustomer() {

        ResponseEntity<Object> responseEntity = customerController.deleteCustomerById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //for delete we can also check if delete api is hit just once
        verify(customerService, times(1)).deleteCustomerById(anyLong());

    }
}