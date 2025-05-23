package paymentsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import paymentsystem.model.enums.CustomerStatus;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Integer customerId;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String finCode;
    String phoneNumber;
    String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    LocalDate registrationDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    CustomerStatus status;
}
