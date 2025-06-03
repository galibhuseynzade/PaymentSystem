package paymentsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.UserRole;
import paymentsystem.model.enums.UserStatus;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
    UserRole role;
    LocalDate registrationDate;
    UserStatus status;
}
