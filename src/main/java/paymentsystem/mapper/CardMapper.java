package paymentsystem.mapper;

import org.mapstruct.Mapper;
import paymentsystem.model.dto.CardDto;
import paymentsystem.model.entity.CardEntity;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardEntity mapToCardEntity(CardDto cardDto);
    CardDto mapToCardDto(CardEntity cardEntity);
}
