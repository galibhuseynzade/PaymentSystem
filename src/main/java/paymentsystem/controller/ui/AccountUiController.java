package paymentsystem.controller.ui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountUiController {

    @GetMapping("/accounts")
    public String getAccountsPage(Model model) {
        model.addAttribute("page", "accounts");
        return "layout";
    }
}
