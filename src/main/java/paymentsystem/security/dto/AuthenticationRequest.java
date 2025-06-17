package paymentsystem.security.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
    String username;
    String password;
}
