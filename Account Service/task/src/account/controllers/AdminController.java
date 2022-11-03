package account.controllers;

import account.model.DTO.UserRoleInfoDTO;
import account.model.DTO.UserStatusDTO;
import account.model.DTO.UserViewResponseDTO;
import account.model.User;
import account.service.AdminService;
import account.service.SecurityEventsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    SecurityEventsService securityEventsService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PutMapping("user/role")
    UserViewResponseDTO changesUserRoles(@RequestBody UserRoleInfoDTO newRoleInfo) {
        User u = adminService.changeUserRole(newRoleInfo);
        return new UserViewResponseDTO(u);
    }

    @DeleteMapping("user/{email}")
    public Map<String, String> deleteUser(
            @PathVariable
            @Pattern(regexp = ".*@acme\\.com", message = "Wrong email format!!")
                    String email) {

        adminService.deleteUser(email);
        return Map.of("user", email, "status", "Deleted successfully!");

    }

    @GetMapping("user")
    public List<UserViewResponseDTO> getAllUser() {
        return adminService.findAllUser();
    }

    @PutMapping("user/access")
    public Map<String, String> setNewStatusUser(@RequestBody @Valid UserStatusDTO newUserStatusDTO) {
        adminService.changeUserStatus(newUserStatusDTO);
        return Map.of("status", String.format("User %s %sed!", newUserStatusDTO.getUserEmail(),
                newUserStatusDTO.getOperation().name().toLowerCase()));
    }

}
