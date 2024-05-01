package com.project.electricitymanagement.unittest.service;

import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.CustomerRepository;
import com.project.electricitymanagement.repository.MeterRepository;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import com.project.electricitymanagement.repository.SupplierRepository;
import com.project.electricitymanagement.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Test class for Customer Service.
 */
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = com.project.electricitymanagement.config.ModelMapperConfig.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MeterRepository meterRepository;

    @Mock
    private SupplierRepository supplierRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PricePerUnitRepository pricePerUnitRepository;
    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private CustomerDto testCustomerDto;
    private Meter meter;
    private Supplier supplier;

    @BeforeEach
    void setUp() {
        meter = new Meter(1L, 2, 234D);
        supplier = new Supplier(1L, "Danish", "Urban");
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setName("Rahul");
        testCustomer.setAddress("Lucknow");
        testCustomer.setConnectionDate(LocalDate.of(2024, 1, 1));
        testCustomer.setLastReading(100.0);
        testCustomer.setCurrentReading(150.0);
        testCustomer.setMeter(meter);
        testCustomer.setSupplier(supplier);


        testCustomerDto = new CustomerDto();
        testCustomerDto.setName("Rahul");
        testCustomerDto.setAddress("Lucknow");
        testCustomerDto.setConnectionDate(LocalDate.of(2024, 1, 1));
        testCustomerDto.setLastReading(100.0);
        testCustomerDto.setCurrentReading(150.0);
        testCustomerDto.setMeterId(1L);
        testCustomerDto.setSupplierId(1L);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(testCustomer);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(testCustomer, result.get(0));
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals(testCustomer, result);
    }

    @Test
    void testGetCustomerById_ResourceNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void testCreateCustomer() {
        // Mocking dependencies
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(meter));
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        when(pricePerUnitRepository.findByUnitConsumed(anyDouble())).thenReturn(Optional.of(5.0));


        Customer result = customerService.createCustomer(testCustomerDto);

        assertNotNull(result);
        assertEquals("Rahul", result.getName());
        assertEquals("Lucknow", result.getAddress());
        assertEquals(LocalDate.of(2024, 1, 1), result.getConnectionDate());
        assertEquals(100.0, result.getLastReading());
        assertEquals(150.0, result.getCurrentReading());
        assertEquals(1L, result.getMeter().getId());
        assertEquals(1L, result.getSupplier().getId());
    }

    @Test
    void testUpdateCustomer() {

        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(testCustomer));
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(meter));
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        when(pricePerUnitRepository.findByUnitConsumed(anyDouble())).thenReturn(Optional.of(5.0));

        TypeMap<CustomerDto, Customer> mockTypeMap = mock(TypeMap.class);
        when(modelMapper.typeMap(CustomerDto.class, Customer.class)).thenReturn(mockTypeMap);

        Customer updatedCustomer = customerService.updateCustomer(customerId, testCustomerDto);

        assertNotNull(updatedCustomer);
        assertEquals("Rahul", updatedCustomer.getName());
        assertEquals("Lucknow", updatedCustomer.getAddress());
        assertEquals(LocalDate.of(2024, 1, 1), updatedCustomer.getConnectionDate());
        assertEquals(100.0, updatedCustomer.getLastReading());
        assertEquals(150.0, updatedCustomer.getCurrentReading());
        assertEquals(1L, updatedCustomer.getMeter().getId());
        assertEquals(1L, updatedCustomer.getSupplier().getId());
    }

    @Test
    void testUpdateCustomer_ResourceNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(1L, testCustomerDto));
    }

    @Test
    void testDeleteCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        ResponseEntity<Object> result = customerService.deleteCustomerById(1L);

        assertNotNull(result);
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    void testDeleteCustomerById_ResourceNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomerById(1L));
    }

    @Test
    void testCalculateBillAmount() {

        double lastReading = 100.0;
        double currentReading = 150.0;
        double unitsConsumed = currentReading - lastReading;
        double pricePerUnit = 5.0; // Assuming a price of $5 per unit
        double minBillAmount = 50.0; // Assuming a minimum bill amount of $50

        Meter meter = new Meter();
        meter.setMinBillAmount(minBillAmount);

        when(meterRepository.findById(1L)).thenReturn(Optional.of(meter));
        when(pricePerUnitRepository.findByUnitConsumed(unitsConsumed)).thenReturn(Optional.of(pricePerUnit));

        double expectedBillAmount = (unitsConsumed * pricePerUnit) + minBillAmount;
        double actualBillAmount = customerService.calculateBillAmount(lastReading, currentReading, 1L);

        assertEquals(expectedBillAmount, actualBillAmount);
    }

    @Test
    void testCalculateBillAmount_UnitConsumedNotFound() {
        double lastReading = 100.0;
        double currentReading = 150.0;
        when(pricePerUnitRepository.findByUnitConsumed(anyDouble())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.calculateBillAmount(lastReading, currentReading, 1L));
    }

    @Test
    void testCalculateBillAmount_MinBillAmountNotFound() {

        double lastReading = 100.0;
        double currentReading = 150.0;

        assertThrows(ResourceNotFoundException.class, () -> customerService.calculateBillAmount(lastReading, currentReading, 1L));
    }
}