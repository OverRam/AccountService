package account.service;

import account.model.DTO.SecurityEventsDTO;
import account.model.DTO.UserRoleInfoDTO;
import account.model.DTO.UserStatusDTO;
import account.model.DTO.UserViewResponseDTO;
import account.model.Role;
import account.model.SecurityEvents;
import account.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SecurityEventsService securityEventsService;



    public List<UserViewResponseDTO> findAllUser() {
        return userService.findAll().stream().map(UserViewResponseDTO::new).collect(Collectors.toList());
    }

    public void deleteUser(String email) {
        User u = findUserByEmail(email);
        if (u.getRoles().get(0) == Role.ROLE_ADMIN)
            throw new IllegalArgumentException("Can't remove ADMINISTRATOR role!");
        userService.delete(u);

        //start log info
        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        String apiPatch = httpServletRequest.getServletPath().replaceFirst("/" + email, "");
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(SecurityEvents.DELETE_USER, subject,
                email, apiPatch);
        log.info(e);
        securityEventsService.saveLog(e);
        //end log info
    }

    public User changeUserRole(UserRoleInfoDTO infoDTO) {
        UserRoleInfoDTO newRoleInfo = UserRoleInfoDTO.getNewInstance(infoDTO);
        boolean isGrant = newRoleInfo.getOperation().equalsIgnoreCase("GRANT");

        if (!newRoleInfo.getRole().startsWith("ROLE_")) {
            newRoleInfo.setRole("ROLE_" + newRoleInfo.getRole());
        }

        //true if new role is in enum set
        boolean isCorrectRole = Arrays.stream(Role.values()).anyMatch(r -> r.name().equals(newRoleInfo.getRole()));
        if (!isCorrectRole)
            throw new IllegalArgumentException("Role not found!");

        User u = findUserByEmail(newRoleInfo.getEmail());

        boolean isAdminRole = Role.valueOf(newRoleInfo.getRole()) == Role.ROLE_ADMIN;

        //true if it is admin
        boolean isUserHaveAdminRole = u.getRoles().stream().anyMatch(r -> r == Role.ROLE_ADMIN);

        //GRANT
        if (isGrant) {
            // admin grant to user || user grant to admin
            if (isAdminRole && !isUserHaveAdminRole || !isAdminRole && isUserHaveAdminRole)
                throw new IllegalArgumentException("The user cannot combine administrative and business roles!");

            u.getRoles().add(Role.valueOf(newRoleInfo.getRole()));
            //REMOVE
        } else {
            if (isUserHaveAdminRole)
                throw new IllegalArgumentException("Can't remove ADMINISTRATOR role!");

            //true if user does not have the role
            boolean isUserHaveThatRole = u.getRoles().stream().anyMatch(r -> r.name().equals(newRoleInfo.getRole()));

            if (!isUserHaveThatRole)
                throw new IllegalArgumentException("The user does not have a role!");

            if (u.getRoles().size() == 1)
                throw new IllegalArgumentException("The user must have at least one role!");

            u.getRoles().remove(Role.valueOf(newRoleInfo.getRole()));
        }

        userService.save(u);

        //start log info
        SecurityEvents event = SecurityEvents.GRANT_ROLE;
        if (newRoleInfo.getOperation().equalsIgnoreCase("REMOVE"))
            event = SecurityEvents.REMOVE_ROLE;

        boolean isRemove = event == SecurityEvents.REMOVE_ROLE;
        String object = String.format("%s role %s %s %s", isRemove ? "Remove" : "Grant", newRoleInfo.getRole(),
                isRemove ? "from" : "to", newRoleInfo.getEmail());

        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        String apiPatch = httpServletRequest.getServletPath();
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(event, subject,
                object, apiPatch);
        log.info(e);
        securityEventsService.saveLog(e);
        //end log info
        return u;
    }

    private User findUserByEmail(String email) {
        return userService.loadByEmail(email);
    }

    public void changeUserStatus(UserStatusDTO newStatus) {
        User u = userService.loadByEmail(newStatus.getUserEmail());

        if (u.getRoles().contains(Role.ROLE_ADMIN))
            throw new IllegalArgumentException("Can't lock the ADMINISTRATOR!");

        SecurityEvents event;
        String object;

        switch (newStatus.getOperation()) {
            case LOCK:
                u.setAccountNonLocked(false);
                event = SecurityEvents.LOCK_USER;
                object = "Lock user " + u.getEmail();
                break;
            case UNLOCK:
                u.setAccountNonLocked(true);
                event = SecurityEvents.UNLOCK_USER;
                object = "Unlock user " + u.getEmail();
                break;
            default:
                throw new IllegalArgumentException("Wrong operation name!!");
        }

        userService.save(u);

        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        String apiPatch = httpServletRequest.getServletPath();
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(event, subject,
                object, apiPatch);

        log.info(e);
        securityEventsService.saveLog(e);
    }

}
