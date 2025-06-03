package paymentsystem.service;

import paymentsystem.model.dto.CardDto;

import java.math.BigDecimal;
import java.util.List;

public interface CardService {
    CardDto createCard(Integer customerId);
    void activateCard(String cardNumber);
    void depositCard(String cardNumber, BigDecimal amount);
    List<CardDto> getCardsByCustomerId(Integer customerId);
    List<CardDto> getAllActiveCards();
    List<CardDto> getAllCards();
}
