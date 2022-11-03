package account.service;

import account.Repository.FailedLoginRepo;
import account.model.DTO.SecurityEventsDTO;
import account.model.FailedLogin;
import account.model.Role;
import account.model.SecurityEvents;
import account.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Service
public class LoginAttemptService {
    @Autowired
    private UserService userService;

    @Autowired
    private FailedLoginRepo loginRepo;

    @Autowired
    private AdminService adminService;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    SecurityEventsService securityEventsService;

    private final int MAX_ATTEMPT = 5;

    public void loginSucceeded(String email) {
        User u = userService.loadByEmail(email);
        FailedLogin loginLog = getFailedLogin(u);
        loginLog.setAttempt(0);
        loginRepo.save(loginLog);
    }

    public void loginFailed(String email) {
        User u = userService.loadByEmail(email);

        FailedLogin failedLogin = getFailedLogin(u);
        failedLogin.setAttempt(failedLogin.getAttempt() + 1);

        loginRepo.save(failedLogin);
        makeLogFailed(failedLogin, u);
    }


    private void makeLogFailed(FailedLogin failedLogin, User u) {
        SecurityEvents event = SecurityEvents.LOGIN_FAILED;

        if (failedLogin.getAttempt() == 5) {
            event = SecurityEvents.BRUTE_FORCE;
        } else if (failedLogin.getAttempt() > 5 && !u.getRoles().contains(Role.ROLE_ADMIN)) {
            u.setAccountNonLocked(false);
            userService.save(u);
            event = SecurityEvents.LOCK_USER;
        }

        String apiPatch = httpServletRequest.getServletPath();
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(event, u.getEmail(),
                apiPatch, apiPatch);

        log.info(e);
        securityEventsService.saveLog(e);
    }

    public FailedLogin getFailedLogin(User u) {
        return loginRepo.findByUserId(u.getId()).orElseGet(() -> {
            FailedLogin f = new FailedLogin();
            f.setUser(u);
            f.setAttempt(0);
            return f;
        });
    }
}
