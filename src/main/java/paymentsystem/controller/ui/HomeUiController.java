package paymentsystem.controller.ui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HomeUiController {
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("page", "home");
        return "layout";
    }
}
