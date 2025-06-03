package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import paymentsystem.mapper.CustomerMapper;
import paymentsystem.model.dto.CustomerDto;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.enums.CustomerStatus;
import paymentsystem.repository.CustomerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        CustomerEntity customerEntity = customerMapper.mapToCustomerEntity(customerDto);
        customerEntity.setRegistrationDate(LocalDate.now());
        customerEntity.setStatus(CustomerStatus.REGULAR);
        customerRepository.save(customerEntity);
        log.info("Customer created " + customerEntity.getCustomerId());
        return customerMapper.mapToCustomerDto(customerEntity);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::mapToCustomerDto).collect(Collectors.toList());
    }
}
