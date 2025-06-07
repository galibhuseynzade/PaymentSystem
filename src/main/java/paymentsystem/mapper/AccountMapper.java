package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.AccountDto;
import paymentsystem.model.entity.AccountEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto mapToAccountDto(AccountEntity accountEntity);

    default AccountEntity buildAccountEntity(CustomerEntity customerEntity) {
        return AccountEntity.builder()
                .accountNumber(generateAccountNumber())
                .customerEntity(customerEntity)
                .balance(BigDecimal.ZERO)
                .openingDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(5))
                .status(AccountStatus.NEW)
                .build();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("AZ");

        for (int i = 0; i < 18; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return stringBuilder.toString();
    }

    default List<AccountDto> getAccountDtoList(List<AccountEntity> accountEntityList) {
        List<AccountDto> accountDtoList = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntityList) {
            accountDtoList.add(getAccountDto(accountEntity));
        }
        return accountDtoList;
    }

    default AccountDto getAccountDto(AccountEntity accountEntity) {
        AccountDto accountDto = mapToAccountDto(accountEntity);
        accountDto.setCustomerId(accountEntity.getCustomerEntity().getCustomerId());
        return accountDto;
    }
}
