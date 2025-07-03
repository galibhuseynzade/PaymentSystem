package paymentsystem.controller.ui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthUiController {
    @GetMapping("/login")
    String loginPage() {
        return "login";
    }
}
