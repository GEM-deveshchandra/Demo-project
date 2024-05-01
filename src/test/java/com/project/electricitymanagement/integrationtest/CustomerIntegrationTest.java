package com.project.electricitymanagement.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import com.project.electricitymanagement.entity.Meter;
import com.project.electricitymanagement.entity.PricePerUnit;
import com.project.electricitymanagement.entity.Supplier;
import com.project.electricitymanagement.repository.CustomerRepository;
import com.project.electricitymanagement.repository.MeterRepository;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import com.project.electricitymanagement.repository.SupplierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for Customer feature
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerIntegrationTest extends AbstractContainerTest {

    private static final String CUSTOMER_API_URL = "/api/customers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PricePerUnitRepository pricePerUnitRepository;

    private Meter savedMeter;
    private Supplier savedSupplier;

    @BeforeEach
    void resetDatabase() {
        customerRepository.deleteAll();
        meterRepository.deleteAll();
        supplierRepository.deleteAll();
        pricePerUnitRepository.deleteAll();

        //Initializing the Meter, Supplier and PricePerUnit Object

        Meter meter = new Meter();
        meter.setLoadAmount(3);
        meter.setMinBillAmount(500d);
        savedMeter = meterRepository.save(meter);


        Supplier supplier = new Supplier();
        supplier.setName("Ramesh");
        supplier.setSupplierType("Urban");
        savedSupplier = supplierRepository.save(supplier);

        PricePerUnit pricePerUnit = new PricePerUnit();
        pricePerUnit.setUnitRangeLower(0);
        pricePerUnit.setUnitRangeUpper(100);
        pricePerUnit.setPrice(3d);
        pricePerUnitRepository.save(pricePerUnit);
    }

    @Test
     void testCreateCustomer_ValidInput() throws Exception {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Rahul");
        customerDto.setAddress("Gurugram");
        customerDto.setConnectionDate(LocalDate.of(2024, 1, 1));
        customerDto.setMeterId(savedMeter.getId());
        customerDto.setSupplierId(savedSupplier.getId());
        customerDto.setLastReading(100d);
        customerDto.setCurrentReading(150d);

        mockMvc.perform(post(CUSTOMER_API_URL)
                        .content(objectMapper.writeValueAsString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(customerDto.getName())))
                .andExpect(jsonPath("$.connectionDate",is(customerDto.getConnectionDate().toString())))
                .andExpect(jsonPath("$.address",is(customerDto.getAddress())))
                .andExpect(jsonPath("$.billAmount", greaterThan(0.0)));
    }

    @Test
     void testCreateCustomer_InvalidInput() throws Exception {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("Rahul");

        mockMvc.perform(post(CUSTOMER_API_URL)
                        .content(objectMapper.writeValueAsString(customerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setName("Rahul");
        customer1.setAddress("Lucknow");
        customer1.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer1.setLastReading(100d);
        customer1.setCurrentReading(150d);
        customer1.setMeter(savedMeter);
        customer1.setSupplier(savedSupplier);

        Customer customer2 = new Customer();
        customer2.setName("Himanshu");
        customer2.setAddress("Bihar");
        customer2.setLastReading(200d);
        customer2.setCurrentReading(250d);
        customer2.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer2.setMeter(savedMeter);
        customer2.setSupplier(savedSupplier);

        customerRepository.saveAll(List.of(customer1, customer2));

        mockMvc.perform(get(CUSTOMER_API_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(customer1.getName())))
                .andExpect(jsonPath("$[0].address",is(customer1.getAddress())))
                .andExpect(jsonPath("$[0].connectionDate",is(customer1.getConnectionDate().toString())))
                .andExpect(jsonPath("$[1].name", is(customer2.getName())))
                .andExpect(jsonPath("$[1].address",is(customer2.getAddress())))
                .andExpect(jsonPath("$[1].connectionDate",is(customer2.getConnectionDate().toString())));
    }

    @Test
     void testGetCustomerById() throws Exception {
        Customer customer = new Customer();
        customer.setName("Avaneesh");
        customer.setAddress("Lucknow");
        customer.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer.setLastReading(100d);
        customer.setCurrentReading(150d);
        customer.setMeter(savedMeter);
        customer.setSupplier(savedSupplier);

        Customer savedCustomer = customerRepository.save(customer);

        mockMvc.perform(get(CUSTOMER_API_URL + "/{id}", savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedCustomer.getId().intValue())))
                .andExpect(jsonPath("$.name", is(savedCustomer.getName())))
                .andExpect(jsonPath("$.address",is(savedCustomer.getAddress())))
                .andExpect(jsonPath("$.connectionDate",is(savedCustomer.getConnectionDate().toString())));
    }

    @Test
     void testGetCustomerById_NotFound() throws Exception {
        mockMvc.perform(get(CUSTOMER_API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }

    @Test
     void testUpdateCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Avaneesh");
        customer.setAddress("Lucknow");
        customer.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer.setLastReading(100d);
        customer.setCurrentReading(150d);
        customer.setMeter(savedMeter);
        customer.setSupplier(savedSupplier);
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDto updatedCustomerDto = new CustomerDto();
        updatedCustomerDto.setName("Rahul");
        updatedCustomerDto.setAddress("Delhi");
        updatedCustomerDto.setConnectionDate(LocalDate.of(2024, 1, 1));
        updatedCustomerDto.setLastReading(200d);
        updatedCustomerDto.setCurrentReading(250d);
        updatedCustomerDto.setMeterId(savedMeter.getId());
        updatedCustomerDto.setSupplierId(savedSupplier.getId());


        mockMvc.perform(put(CUSTOMER_API_URL + "/{id}", savedCustomer.getId())
                        .content(objectMapper.writeValueAsString(updatedCustomerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(savedCustomer.getId().intValue())))
                .andExpect(jsonPath("$.name", is(updatedCustomerDto.getName())))
                .andExpect(jsonPath("$.address",is(updatedCustomerDto.getAddress())))
                .andExpect(jsonPath("$.connectionDate",is(updatedCustomerDto.getConnectionDate().toString())));
    }

    @Test
    void testUpdateCustomer_NotFound() throws Exception {
        CustomerDto updatedCustomerDto = new CustomerDto();
        updatedCustomerDto.setName("Rahul");
        updatedCustomerDto.setAddress("Lucknow");
        updatedCustomerDto.setConnectionDate(LocalDate.of(2024, 2, 1));
        updatedCustomerDto.setLastReading(200d);
        updatedCustomerDto.setCurrentReading(250d);
        updatedCustomerDto.setMeterId(savedMeter.getId());
        updatedCustomerDto.setSupplierId(savedSupplier.getId());

        mockMvc.perform(put(CUSTOMER_API_URL + "/{id}", 100)
                        .content(objectMapper.writeValueAsString(updatedCustomerDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
     void testDeleteCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mahesh");
        customer.setAddress("Lucknow");
        customer.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer.setLastReading(100d);
        customer.setCurrentReading(150d);
        customer.setMeter(savedMeter);
        customer.setSupplier(savedSupplier);
        Customer savedCustomer = customerRepository.save(customer);

        mockMvc.perform(delete(CUSTOMER_API_URL + "/{id}", savedCustomer.getId()))
                .andExpect(status().isOk());

        assertFalse(customerRepository.existsById(savedCustomer.getId()));
    }

    @Test
     void testDeleteCustomer_NotFound() throws Exception {
        mockMvc.perform(delete(CUSTOMER_API_URL + "/{id}", 100))
                .andExpect(status().isNotFound());
    }
    @Test
     void testGetCustomerBillById() throws Exception {
        Customer customer = new Customer();
        customer.setName("Mahesh");
        customer.setAddress("Lucknow");
        customer.setConnectionDate(LocalDate.of(2024, 1, 1));
        customer.setLastReading(100d);
        customer.setCurrentReading(150d);
        customer.setMeter(savedMeter);
        customer.setSupplier(savedSupplier);
        Customer savedCustomer = customerRepository.save(customer);

        mockMvc.perform(get(CUSTOMER_API_URL + "/{id}/bill", savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", greaterThan(0.0)));
    }

    @Test
     void testGetCustomerBillById_NotFound() throws Exception {
        mockMvc.perform(get(CUSTOMER_API_URL + "/{id}/bill", 100))
                .andExpect(status().isNotFound());
    }

}
