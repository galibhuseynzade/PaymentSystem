package paymentsystem.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.UserRole;
import paymentsystem.model.enums.UserStatus;

import java.time.LocalDate;

@Table(name = "users")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    String username;
    String password;

    @Enumerated(EnumType.STRING)
    UserRole role;

    LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    UserStatus status;
}

