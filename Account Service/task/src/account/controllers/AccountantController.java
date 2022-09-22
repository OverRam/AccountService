package account.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acct")
public class AccountantController {

    @PostMapping("/payments")
    String uploadsPayrolls() {
        return "";
    }

    @PutMapping("/payments")
    String updatesPaymentInformation() {
        return "";
    }
}
