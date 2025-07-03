package paymentsystem.controller.ui;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionUiController {

    @GetMapping("/transactions")
    public String getTransactionsPage(Model model) {
        model.addAttribute("page", "transactions");
        return "layout";
    }
}
