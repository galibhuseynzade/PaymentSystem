package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import paymentsystem.model.dto.CardDto;

import java.math.BigDecimal;

public interface CardService {
    CardDto createCard(Integer customerId);
    Boolean activateCard(String cardNumber);
    Boolean depositCard(String cardNumber, BigDecimal amount);
    Page<CardDto> getCardsByCustomerId(Integer customerId, Integer page, Integer size);
    Page<CardDto> getAllActiveCards(Integer size, Integer page);
    Page<CardDto> getAllCards(Integer size, Integer page);
}
