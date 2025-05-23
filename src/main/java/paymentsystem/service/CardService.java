package paymentsystem.service;

import paymentsystem.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {
    CardDto createCard(Integer customerId);
    String activateCard(String cardNumber);
    String depositCard(String cardNumber, BigDecimal amount);
    List<CardDto> findCardsByCustomerId(Integer customerId);
    List<CardDto> findAllActiveCards();
    List<CardDto> findAllCards();
}
