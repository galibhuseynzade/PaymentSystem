package paymentsystem.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LimitProperties {
    @Value("${limits.maxAccountCount}")
    Integer maxAccountCount;

    @Value("${limits.maxCardCount}")
    Integer maxCardCount;

    @Value("${limits.dailyTransactionLimit}")
    BigDecimal dailyTransactionLimit;

    @Value("${limits.minAcceptableAccountBalance}")
    BigDecimal minAcceptableAccountBalance;

    @Value("${limits.minAcceptableCardBalance}")
    BigDecimal minAcceptableCardBalance;
}
