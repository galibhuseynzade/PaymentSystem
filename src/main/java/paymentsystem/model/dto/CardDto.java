package paymentsystem.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDto {
    String cardNumber;
    Integer customerId;
    BigDecimal balance;
    LocalDate issueDate;
    LocalDate expireDate;
    CardStatus status;
}
