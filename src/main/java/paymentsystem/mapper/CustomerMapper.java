package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.CustomerDto;
import paymentsystem.model.entity.CustomerEntity;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerEntity mapToCustomerEntity(CustomerDto customerDto);
    CustomerDto mapToCustomerDto(CustomerEntity customerEntity);
}
