package account.controllers;

import account.model.DTO.UserRoleInfoDTO;
import account.model.DTO.UserViewResponseDTO;
import account.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PutMapping("/user/role")
    UserViewResponseDTO changesUserRoles(@RequestBody UserRoleInfoDTO newRoleInfo) {
        return new UserViewResponseDTO(adminService.changeUserRole(newRoleInfo));
    }

    @DeleteMapping("/user/{email}")
    Map<String, String> deleteUser(
            @PathVariable
            @Pattern(regexp = ".*@acme\\.com", message = "Wrong email format!!")
                    String email) {

        adminService.deleteUser(email);
        return Map.of("user", email, "status", "Deleted successfully!");

    }

    @GetMapping("/user")
    List<UserViewResponseDTO> getAllUser() {
        return adminService.findAllUser();
    }
}
