package paymentsystem.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.model.dto.CardDto;
import paymentsystem.service.abstraction.CardService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {
    CardService cardService;

    @GetMapping
    public Page<CardDto> getAllCards(@RequestParam(defaultValue = "0", required = false) Integer page,
                                     @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getAllCards(pageable);
    }

    @GetMapping("/allActiveCards")
    public Page<CardDto> getAllActiveCards(@RequestParam(defaultValue = "0", required = false) Integer page,
                                           @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getAllActiveCards(pageable);
    }

    @GetMapping("/cardsByCustomerId/{customerId}")
    public Page<CardDto> getCardsByCustomerId(@PathVariable Integer customerId,
                                              @RequestParam(defaultValue = "0", required = false) Integer page,
                                              @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return cardService.getCardsByCustomerId(customerId, pageable);
    }

    @PostMapping("/{customerId}")
    public CardDto createCard(@PathVariable Integer customerId) {
        return cardService.createCard(customerId);
    }

    @PutMapping("/activateCard/{cardNumber}")
    public void activateCard(@PathVariable String cardNumber) {
        cardService.activateCard(cardNumber);
    }

    @PutMapping("/depositCard/{cardNumber}")
    public void depositCard(@PathVariable String cardNumber, @RequestParam BigDecimal amount) {
        cardService.depositCard(cardNumber, amount);
    }
}
