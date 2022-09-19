package account.controllers;

import account.HackedPassword;
import account.Role;
import account.RoleRepo;
import account.errors.PasswordError;
import account.service.UserDetailsServiceImpl;
import account.user.User;
import account.user.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    UserDetailsServiceImpl service;

    @Autowired
    PasswordEncoder encoder;


    @PostMapping("/signup")
    UserView signupToService(@Valid @RequestBody User userInfo) {
        userInfo.addAuthority(new Role(RoleRepo.USER.getAuthority()));
        service.saveUser(userInfo);

        User user = (User) service.loadUserByEmail(userInfo.getEmail());
        return new UserView(user);
    }

    @PostMapping("/changepass")
    ResponseEntity<Object> changepass(@AuthenticationPrincipal UserDetails auth,
                                      @RequestBody @Valid Password newPassword) throws PasswordError {

        User user = (User) service.loadUserByUsername(auth.getUsername());

        service.changePassword(newPassword.getNew_password(), user);

        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("email", user.getUsername());
        response.put("status", "The password has been updated successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    void findAll() {
        service.findAllUsers().forEach(u -> System.out.println(u.toString()));
        System.out.println("_______________________________________________");
    }


    private static class Password {
        @NotNull(message = "The password length must be at least 12 chars!")
        @Size(min = 12, message = "The password length must be at least 12 chars!")
        private String new_password;

        public String getNew_password() {
            return new_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }
    }
}