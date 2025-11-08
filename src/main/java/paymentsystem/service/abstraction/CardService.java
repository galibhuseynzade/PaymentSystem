package paymentsystem.service.abstraction;

import paymentsystem.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {
    CardDto createCard(Integer customerId);
    Boolean activateCard(String cardNumber);
    Boolean depositCard(String cardNumber, BigDecimal amount);
    List<CardDto> getCardsByCustomerId(Integer customerId, Integer page, Integer size);
    List<CardDto> getAllActiveCards(Integer page, Integer size);
    List<CardDto> getAllCards(Integer page, Integer size);
    CardDto getCardByCardNumber(String cardNumber);
}
