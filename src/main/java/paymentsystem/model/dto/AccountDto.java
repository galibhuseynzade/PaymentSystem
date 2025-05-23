package paymentsystem.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDto {
    String accountNumber;
    Integer customerId;
    BigDecimal balance;
    LocalDate openingDate;
    LocalDate expireDate;
    AccountStatus status;
}
