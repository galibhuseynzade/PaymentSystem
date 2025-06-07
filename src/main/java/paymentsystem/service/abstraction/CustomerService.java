package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import paymentsystem.model.dto.CustomerDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerDto customerDto);
    Page<CustomerDto> getAllCustomers(Integer size, Integer page);
}
