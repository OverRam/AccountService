package account.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {


    @PutMapping("/user/role")
    String changesUserRoles() {
        return "zmiana roli";
    }

    @DeleteMapping("/user")
    String deleteUser() {
        return "usuniecie roli";

    }

    @GetMapping("/user")
    String showInformationUsers() {
        return "info";
    }
}
