package account.controllers;

import account.model.DTO.UserViewResponseDTO;
import account.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empl")
@RequiredArgsConstructor
public class EmployeeController {

    @GetMapping("/payment")
    public ResponseEntity<UserViewResponseDTO> payment(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(new UserViewResponseDTO(user));
    }
}
