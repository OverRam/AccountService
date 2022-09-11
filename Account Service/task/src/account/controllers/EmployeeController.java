package account.controllers;

import account.service.UserDetailsServiceImpl;
import account.user.User;
import account.user.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
public class EmployeeController {

    @Autowired
    UserDetailsServiceImpl service;

    @GetMapping("/payment")
    UserView getEmployeePayrolls(Authentication auth) {
        System.out.println(auth.getName());
        User u = (User) service.loadUserByUsername(auth.getName());

        return new UserView(u);
    }
}
