package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.TransactionDto;
import paymentsystem.model.entity.TransactionEntity;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionEntity mapToTransactionEntity(TransactionDto transactionDto);
    TransactionDto mapToTransactionDto(TransactionEntity transactionEntity);
}
