package account.controllers;

import account.model.DTO.SecurityEventsDTO;
import account.service.SecurityEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/security/")
public class SecurityController {

    @Autowired
    SecurityEventsService securityEventsService;

    @GetMapping("events")
    public List<SecurityEventsDTO> getEvents() {
        return securityEventsService.getEventsLogg();
    }
}
