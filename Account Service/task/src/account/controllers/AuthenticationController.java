package account.controllers;

import account.model.DTO.NewPasswordDTO;
import account.model.DTO.NewUserDTO;
import account.model.DTO.PasswordChangedResponseDTO;
import account.model.DTO.UserViewResponseDTO;
import account.model.User;
import account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserViewResponseDTO> signup(@Valid @RequestBody NewUserDTO newUser) {
        return ResponseEntity.ok(authService.signup(newUser));
    }

    @PostMapping("/changepass")
    public ResponseEntity<PasswordChangedResponseDTO> changepass(@AuthenticationPrincipal User currentUser,
                                                                 @Valid @RequestBody NewPasswordDTO newPassword) {
        return ResponseEntity.ok(authService.changePass(currentUser, newPassword));
    }
}