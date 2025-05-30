package paymentsystem.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import paymentsystem.config.DefaultValueConfiguration;
import paymentsystem.config.LimitConfiguration;
import paymentsystem.exception.exceptions.CardNotFoundException;
import paymentsystem.exception.exceptions.CustomerNotFoundException;
import paymentsystem.exception.exceptions.InactiveCardDepositException;
import paymentsystem.exception.exceptions.InvalidCardActivationException;
import paymentsystem.exception.exceptions.MaximumCardCoundException;
import paymentsystem.mapper.CardMapper;
import paymentsystem.model.dto.CardDto;
import paymentsystem.model.entity.CardEntity;
import paymentsystem.model.entity.CustomerEntity;
import paymentsystem.model.enums.CardStatus;
import paymentsystem.repository.CardRepository;
import paymentsystem.repository.CustomerRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;
    CardMapper cardMapper;
    CustomerRepository customerRepository;
    List<CardStatus> validAccountStatusList = Arrays.asList(CardStatus.NEW, CardStatus.ACTIVE);
    LimitConfiguration limitConfiguration;
    DefaultValueConfiguration defaultValueConfiguration;

    @Override
    public CardDto createCard(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        if (cardRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList).size() >= limitConfiguration.getMaxCardCount())
            throw new MaximumCardCoundException(limitConfiguration.getMaxCardCount());

        String cardNumber = generateCardNumber();
        CardEntity cardEntity = CardEntity.builder()
                .cardNumber(cardNumber)
                .customerEntity(customerEntity)
                .balance(BigDecimal.ZERO)
                .issueDate(LocalDate.now())
                .expireDate(LocalDate.now().plusYears(defaultValueConfiguration.getCardPeriodByYears()))
                .status(CardStatus.NEW)
                .build();

        cardRepository.save(cardEntity);
        log.info("Card created " + cardNumber);
        return getCardDto(cardEntity);
    }

    @Override
    public String activateCard(String cardNumber) {
        CardEntity cardEntity = cardRepository.findById(cardNumber).orElseThrow(CardNotFoundException::new);

        if (!cardEntity.getStatus().equals(CardStatus.NEW))
            throw new InvalidCardActivationException();

        cardEntity.setStatus(CardStatus.ACTIVE);
        cardRepository.save(cardEntity);
        log.info("Card activated " + cardNumber);
        return "Card activated successfully";
    }

    @Override
    public String depositCard(String cardNumber, BigDecimal amount) {
        CardEntity cardEntity = cardRepository.findById(cardNumber).orElseThrow(CardNotFoundException::new);
        if (!cardEntity.getStatus().equals(CardStatus.ACTIVE))
            throw new InactiveCardDepositException();

        BigDecimal newBalance = cardEntity.getBalance().add(amount);
        cardEntity.setBalance(newBalance);
        cardRepository.save(cardEntity);
        return "Balance is " + newBalance;
    }

    @Override
    public List<CardDto> findCardsByCustomerId(Integer customerId) {
        List<CardEntity> cardEntityList = cardRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList);
        return getCardDtoList(cardEntityList);
    }

    @Override
    public List<CardDto> findAllActiveCards() {
        List<CardEntity> cardEntityList = cardRepository.findByStatus(CardStatus.ACTIVE);
        return getCardDtoList(cardEntityList);
    }

    @Override
    public List<CardDto> findAllCards() {
        List<CardEntity> cardEntityList = cardRepository.findAll();
        return getCardDtoList(cardEntityList);
    }

    private List<CardDto> getCardDtoList(List<CardEntity> cardEntityList) {
        List<CardDto> cardDtoList = new ArrayList<>();
        for (CardEntity cardEntity : cardEntityList) {
            cardDtoList.add(getCardDto(cardEntity));
        }
        return cardDtoList;
    }

    private CardDto getCardDto(CardEntity cardEntity) {
        CardDto cardDto = cardMapper.mapToCardDto(cardEntity);
        cardDto.setCustomerId(cardEntity.getCustomerEntity().getCustomerId());
        return cardDto;
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
