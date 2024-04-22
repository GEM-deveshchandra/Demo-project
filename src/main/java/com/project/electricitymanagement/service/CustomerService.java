package com.project.electricitymanagement.service;

import com.project.electricitymanagement.config.Constants;
import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import com.project.electricitymanagement.exception.ResourceNotFoundException;
import com.project.electricitymanagement.repository.CustomerRepository;
import com.project.electricitymanagement.repository.MeterRepository;
import com.project.electricitymanagement.repository.PricePerUnitRepository;
import com.project.electricitymanagement.repository.SupplierRepository;
import com.project.electricitymanagement.entity.Meter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service class for managing operations related to customer.
 */
@Service
public class CustomerService {
    /**
     * Defining the logger object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MeterRepository meterRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private PricePerUnitRepository pricePerUnitRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Retrieves list of all the customers in the database.
     *
     * @return List of all the customers.
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by its id from the database.
     *
     * @param id The id of the customer to retrieve.
     * @return The customer with the specified id.
     * @throws ResourceNotFoundException if the customer with the specified id is not found.
     */
    public Customer getCustomerById(final Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.CUSTOMER, Constants.ID, id));
    }

    /**
     * Creates a new customer.
     *
     * @param customerData The customerDto containing information about the new customer.
     * @return The newly created customer.
     */
    public Customer createCustomer(final CustomerDto customerData) {
        validateMeterAndSupplier(customerData);
        Customer customer = new Customer();
        modelMapper.map(customerData, customer);
        double billAmount = calculateBillAmount(customerData.getLastReading(), customerData.getCurrentReading(), customerData.getMeterId());
        customer.setBillAmount(billAmount);
        return customerRepository.save(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param id              The id of the customer to update
     * @param customerDetails The customerDto that contains the updated information of the customer.
     * @return The updated customer
     * @throws ResourceNotFoundException if the customer with the specified id is not found.
     */
    public Customer updateCustomer(final Long id, final CustomerDto customerDetails) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.CUSTOMER, Constants.ID, id));
        validateMeterAndSupplier(customerDetails);

        //If we consider that the connection date should only be set while creating a new customer, then while updating a customer, setting Connection date is skipped.
        modelMapper.typeMap(CustomerDto.class, Customer.class)
                        .addMappings(mapper -> mapper.skip(Customer::setConnectionDate));
        modelMapper.map(customerDetails, customer);
        double billAmount = calculateBillAmount(customerDetails.getLastReading(), customerDetails.getCurrentReading(), customer.getMeter().getId());
        customer.setBillAmount(billAmount);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer by its id.
     *
     * @param id The id of the customer to be deleted.
     * @return Response entity with status of operation.
     * @throws ResourceNotFoundException if the customer with the specified id is not found.
     */
    public ResponseEntity<Object> deleteCustomerById(final Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.CUSTOMER, "id", id));
        customerRepository.delete(customer);
        return ResponseEntity.ok().build();
    }
    /**
     * This method calculates the bill of the customer using the price per unit and minimum bill amount based on the load.
     *
     * @param lastReading    (double) The last reading of the meter.
     * @param currentReading (double) The current reading of the meter.
     * @param meterId        The meter ID of the customer.
     * @return The total bill amount
     */
    public double calculateBillAmount(final double lastReading, final double currentReading, final Long meterId) {

        double unitsConsumed = currentReading - lastReading;
        double pricePerUnit = getPricePerUnit(unitsConsumed);
        double minBillAmount = getMinBillAmount(meterId);
        LOGGER.info(String.format("Calculating bill of customer having meter id %d", meterId));
        return (unitsConsumed * pricePerUnit) + minBillAmount;
    }

    /**
     * This method returns bill of the customer by id.
     *
     * @param id Customer id.
     * @return Total bill of the customer.
     * @throws ResourceNotFoundException if the customer is not found.
     */
    public Double getCustomerBillById(final Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(Constants.CUSTOMER, "id", id));
        return calculateBillAmount(customer.getLastReading(), customer.getCurrentReading(), customer.getMeter().getId());
    }

    /**
     * Retrieves the Minimum bill amount for the given meterID.
     *
     * @param meterId MeterID
     * @return Minimum bill amount
     * @throws ResourceNotFoundException if the meter with the given id is not found.
     */
    private double getMinBillAmount(final Long meterId) {
        Meter meter = meterRepository.findById(meterId).orElseThrow(() -> new ResourceNotFoundException(Constants.METER, Constants.ID, meterId));
        return meter.getMinBillAmount();
    }

    /**
     * Retrieves the price per unit applicable for the customer based on units consumed.
     *
     * @param unitsConsumed units consumed by the customer
     * @return price per unit
     * @throws ResourceNotFoundException if the unit consumed doesn't fit in any range in the database.
     */
    private double getPricePerUnit(final double unitsConsumed) {
        return pricePerUnitRepository.findByUnitConsumed(unitsConsumed).orElseThrow(() -> new ResourceNotFoundException("Price per unit", "unit consumed ", unitsConsumed));
    }

    /**
     * Performs validation of the meter and supplier ID in the customer Dto.
     *
     * @param customerDto CustomerDto
     * @throws ResourceNotFoundException if the meter or the supplier with the given id is not found.
     */
    private void validateMeterAndSupplier(final CustomerDto customerDto) {

        meterRepository.findById(customerDto.getMeterId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.METER, Constants.ID, customerDto.getMeterId()));
        supplierRepository.findById(customerDto.getSupplierId())
                .orElseThrow(() -> new ResourceNotFoundException(Constants.SUPPLIER, Constants.ID, customerDto.getSupplierId()));

    }
}
