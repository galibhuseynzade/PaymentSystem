package paymentsystem.controller.ui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerUiController {
    @GetMapping("/customers")
    public String customerPage(Model model) {
        model.addAttribute("page", "customers");
        return "layout";
    }
}
