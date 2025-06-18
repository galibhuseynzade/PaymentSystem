package paymentsystem.security.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paymentsystem.exception.exceptions.IncorrectPasswordException;
import paymentsystem.exception.exceptions.UserNotFoundException;
import paymentsystem.model.entity.UserEntity;
import paymentsystem.repository.UserRepository;
import paymentsystem.security.dto.AuthenticationResponse;
import paymentsystem.security.jwt.JwtUtil;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    JwtUtil jwtUtil;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestParam String username, @RequestParam String password) {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(password, userEntity.getPassword())) throw new IncorrectPasswordException();

        String token = jwtUtil.generateToken(username);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
