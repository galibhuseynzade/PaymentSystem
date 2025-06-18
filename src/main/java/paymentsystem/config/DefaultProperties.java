package paymentsystem.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DefaultProperties {
    @Value("${defaults.accountLength}")
    Integer defaultAccountLength;

    @Value("${defaults.cardLength}")
    Integer defaultCardLength;

    @Value("${defaults.monthlyPeriod}")
    Integer defaultMonthlyPeriod;
}
