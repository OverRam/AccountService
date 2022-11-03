package account.controllers;

import account.model.DTO.*;
import account.model.SecurityEvents;
import account.model.User;
import account.service.AuthService;
import account.service.SecurityEventsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    AuthService authService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    SecurityEventsService securityEventsService;

    @PostMapping("signup")
    public ResponseEntity<UserViewResponseDTO> signup(@Valid @RequestBody NewUserDTO newUser) {
        UserViewResponseDTO u = authService.signup(newUser);

        //start log info
        SecurityEvents event = SecurityEvents.CREATE_USER;
        String object = newUser.getEmail();
        String subject = "Anonymous";
        String apiPatch = httpServletRequest.getServletPath();
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(event, subject, object, apiPatch);

        log.info(e);
        securityEventsService.saveLog(e);
        //end log info

        return ResponseEntity.ok(u);
    }

    @PostMapping("changePass")
    public ResponseEntity<PasswordChangedResponseDTO> changePass(Authentication authentication,
                                                                 @Valid @RequestBody NewPasswordDTO newPassword) {

        //start log info
        SecurityEvents event = SecurityEvents.CHANGE_PASSWORD;
        String subject = authentication.getName();
        String apiPatch = httpServletRequest.getServletPath();

        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(event, subject, subject, apiPatch);
        log.info(e);
        securityEventsService.saveLog(e);
        //end log info

        User currentUser = (User) authentication.getPrincipal();
        PasswordChangedResponseDTO dto = authService.changePass(currentUser, newPassword);


        return ResponseEntity.ok(dto);

    }
}