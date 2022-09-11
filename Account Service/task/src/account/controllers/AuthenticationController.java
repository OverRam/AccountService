package account.controllers;

import account.Role;
import account.RoleRepo;
import account.service.UserDetailsServiceImpl;
import account.user.User;
import account.user.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    PasswordEncoder encoder;


    @PostMapping("/signup")
    UserView signupToService(@Validated @RequestBody User userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.addAuthority(new Role(RoleRepo.USER.getAuthority()));
        service.saveUser(userInfo);

        User user = (User) service.loadUserByEmail(userInfo.getEmail());
        return new UserView(user);
    }

    void findAll() {
        service.findAllUsers().forEach(u -> System.out.println(u.toString()));
        System.out.println("_______________________________________________");
    }


}