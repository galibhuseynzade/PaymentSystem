package paymentsystem.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefaultValueConfiguration {
    @Value("${defaultValues.accountLength}")
    Integer accountLength;
    @Value("${defaultValues.cardLength}")
    Integer cardLength;
    @Value("${defaultValues.accountPeriodByYears}")
    Integer accountPeriodByYears;
    @Value("${defaultValues.cardPeriodByYears}")
    Integer cardPeriodByYears;
}
