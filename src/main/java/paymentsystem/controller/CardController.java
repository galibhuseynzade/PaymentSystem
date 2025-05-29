package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.CardDto;
import paymentsystem.service.CardService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {
    CardService cardService;

    @GetMapping
    public List<CardDto> findAllCards() {
        return cardService.findAllCards();
    }

    @GetMapping("/findAllActiveCards")
    public List<CardDto> findAllActiveCards() {
        return cardService.findAllActiveCards();
    }

    @GetMapping("/findCardsByCustomerId")
    public List<CardDto> findCardsByCustomerId(@RequestParam Integer customerId) {
        return cardService.findCardsByCustomerId(customerId);
    }

    @PostMapping("/createCard")
    public CardDto createCard(@RequestParam Integer customerId) {
        return cardService.createCard(customerId);
    }

    @PutMapping("/activateCard")
    public String activateCard(@RequestParam String cardNumber) {
        return cardService.activateCard(cardNumber);
    }

    @PutMapping("/depositCard")
    public String depositCard(@RequestParam String cardNumber, @RequestParam BigDecimal amount) {
        return cardService.depositCard(cardNumber, amount);
    }
}
