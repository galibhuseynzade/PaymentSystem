package paymentsystem.service.concrete;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import paymentsystem.config.LimitProperties;
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
import paymentsystem.service.abstraction.CardService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardServiceImpl implements CardService {
    CardRepository cardRepository;
    CardMapper cardMapper;
    CustomerRepository customerRepository;
    List<CardStatus> validAccountStatusList = Arrays.asList(CardStatus.NEW, CardStatus.ACTIVE);
    LimitProperties limitProperties;

    @Override
    public CardDto createCard(Integer customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        if (cardRepository.countByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList) >= limitProperties.getMaxCardCount())
            throw new MaximumCardCoundException(limitProperties.getMaxCardCount());

        CardEntity cardEntity = cardMapper.buildCardEntity(customerEntity);

        cardRepository.save(cardEntity);
        log.info("Card created");
        return cardMapper.getCardDto(cardEntity);
    }

    @Override
    public Boolean activateCard(String cardNumber) {
        CardEntity cardEntity = cardRepository.findById(cardNumber).orElseThrow(CardNotFoundException::new);

        if (!cardEntity.getStatus().equals(CardStatus.NEW))
            throw new InvalidCardActivationException();

        cardEntity.setStatus(CardStatus.ACTIVE);
        cardRepository.save(cardEntity);
        log.info("Card activated " + cardNumber);
        return true;
    }

    @Override
    public Boolean depositCard(String cardNumber, BigDecimal amount) {
        CardEntity cardEntity = cardRepository.findById(cardNumber).orElseThrow(CardNotFoundException::new);
        if (!cardEntity.getStatus().equals(CardStatus.ACTIVE))
            throw new InactiveCardDepositException();

        BigDecimal newBalance = cardEntity.getBalance().add(amount);
        cardEntity.setBalance(newBalance);
        cardRepository.save(cardEntity);
        return true;
    }

    @Override
    public List<CardDto> getCardsByCustomerId(Integer customerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CardEntity> cardEntityPage = cardRepository.findByCustomerEntity_CustomerIdAndStatusIn(customerId, validAccountStatusList, pageable);
        List<CardDto> cardDtoList = cardMapper.getCardDtoList(cardEntityPage.getContent());
        return new PageImpl<>(cardDtoList, pageable, cardEntityPage.getTotalElements()).getContent();
    }

    @Override
    public List<CardDto> getAllActiveCards(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CardEntity> cardEntityPage = cardRepository.findByStatus(CardStatus.ACTIVE, pageable);
        List<CardDto> cardDtoList = cardMapper.getCardDtoList(cardEntityPage.getContent());
        return new PageImpl<>(cardDtoList, pageable, cardEntityPage.getTotalElements()).getContent();
    }

    @Override
    public List<CardDto> getAllCards(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CardEntity> cardEntityPage = cardRepository.findAll(pageable);
        List<CardDto> cardDtoList = cardMapper.getCardDtoList(cardEntityPage.getContent());
        return new PageImpl<>(cardDtoList, pageable, cardEntityPage.getTotalElements()).getContent();
    }
}
