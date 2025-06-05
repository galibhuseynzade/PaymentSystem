package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.CardDto;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardDto mapToCardDto(CardEntity cardEntity);

    default CardEntity buildCardEntity(CustomerEntity customerEntity) {
        return CardEntity.builder()
                .cardNumber(generateCardNumber())
                .customerEntity(customerEntity)
                .balance(BigDecimal.ZERO)
                .issueDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(3))
                .status(CardStatus.NEW)
                .build();
    }

    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int firstDigit = random.nextBoolean() ? 4 : 5;

        stringBuilder.append(firstDigit);
        for (int i = 0; i < 15; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return stringBuilder.toString();
    }

}
