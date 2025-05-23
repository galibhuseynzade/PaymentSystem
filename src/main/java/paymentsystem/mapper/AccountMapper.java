package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.AccountDto;
import paymentsystem.model.entity.AccountEntity;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountEntity mapToAccountEntity(AccountDto accountDto);
    AccountDto mapToAccountDto(AccountEntity accountEntity);
}
