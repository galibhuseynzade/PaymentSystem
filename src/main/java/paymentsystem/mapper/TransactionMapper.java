package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.TransactionDto;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.entity.TransactionEntity;
import paymentsystem.model.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto mapToTransactionDto(TransactionEntity transactionEntity);

    default TransactionEntity buildTransactionEntity(CustomerEntity customerEntity, String debit, String credit, BigDecimal amount) {
        return TransactionEntity.builder()
                .transactionId(generateTransactionId())
                .customerEntity(customerEntity)
                .debit(debit)
                .credit(credit)
                .transactionDate(LocalDate.now())
                .amount(amount)
                .status(TransactionStatus.PENDING)
                .build();
    }

    private String generateTransactionId() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder("TR");
        for (int i = 0; i < 18; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }
}
