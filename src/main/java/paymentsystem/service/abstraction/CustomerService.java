package paymentsystem.service.abstraction;

import paymentsystem.model.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    List<CustomerDto> getAllCustomers(Integer page, Integer size);
    CustomerDto getCustomerById(Integer customerId);
}
