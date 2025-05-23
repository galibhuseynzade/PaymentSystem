package paymentsystem.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.AccountStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "account")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountEntity {
    @Id
    String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    CustomerEntity customerEntity;

    BigDecimal balance;
    LocalDate openingDate;
    LocalDate expireDate;

    @Enumerated(EnumType.STRING)
    AccountStatus status;
}
