package paymentsystem.service.abstraction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import paymentsystem.model.dto.CardDto;

import java.math.BigDecimal;

public interface CardService {
    CardDto createCard(Integer customerId);
    void activateCard(String cardNumber);
    void depositCard(String cardNumber, BigDecimal amount);
    Page<CardDto> getCardsByCustomerId(Integer customerId, Pageable pageable);
    Page<CardDto> getAllActiveCards(Pageable pageable);
    Page<CardDto> getAllCards(Pageable pageable);
}
