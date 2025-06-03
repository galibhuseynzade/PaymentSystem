package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<CardDto> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("/getAllActiveCards")
    public List<CardDto> getAllActiveCards() {
        return cardService.getAllActiveCards();
    }

    @GetMapping("/getCardsByCustomerId/{customerId}")
    public List<CardDto> getCardsByCustomerId(@PathVariable Integer customerId) {
        return cardService.getCardsByCustomerId(customerId);
    }

    @PostMapping("/createCard")
    public CardDto createCard(@RequestParam Integer customerId) {
        return cardService.createCard(customerId);
    }

    @PutMapping("/activateCard")
    public void activateCard(@RequestParam String cardNumber) {
        cardService.activateCard(cardNumber);
    }

    @PutMapping("/depositCard")
    public void depositCard(@RequestParam String cardNumber, @RequestParam BigDecimal amount) {
        cardService.depositCard(cardNumber, amount);
    }
}
