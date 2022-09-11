package account.controllers;

import account.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/acct")
public class AccountantController {

    @Autowired
    UserDetailsServiceImpl service;

    @PostMapping("/payments")
    String uploadsPayrolls() {
        return "";
    }

    @PutMapping("/payments")
    String updatesPaymentInformation() {
        return "";
    }
}
