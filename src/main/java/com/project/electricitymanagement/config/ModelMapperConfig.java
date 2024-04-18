package com.project.electricitymanagement.config;

import com.project.electricitymanagement.dto.CustomerDto;
import com.project.electricitymanagement.entity.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Model Mapper Bean.
 */
@Configuration
public class ModelMapperConfig {
    /**
     * Creates a Bean for Model mapper.
     * @return an instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

/**
 * Define custom mapping for converting from CustomerDto to Customer
 */
        //This was needed due to Foreign key constraint.
        modelMapper.createTypeMap(CustomerDto.class, Customer.class)
                .addMappings(mapper -> {
                    mapper.map(CustomerDto::getMeterId, (dest, value) -> dest.getMeter().setId((Long) value));
                    mapper.map(CustomerDto::getSupplierId, (dest, value) -> dest.getSupplier().setId((Long) value));
                });
        return modelMapper;
    }

}
