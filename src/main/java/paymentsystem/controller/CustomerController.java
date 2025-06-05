package paymentsystem.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.CustomerDto;
import paymentsystem.service.abstraction.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    CustomerService customerService;

    @GetMapping
    public Page<CustomerDto> getAllCustomers(@RequestParam(defaultValue = "0", required = false) Integer page,
                                             @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return customerService.getAllCustomers(pageable);
    }

    @PostMapping
    public CustomerDto createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return customerService.createCustomer(customerDto);
    }
}
