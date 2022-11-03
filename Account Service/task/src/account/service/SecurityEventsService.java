package account.service;

import account.Repository.SecurityEventsRepo;
import account.model.DTO.SecurityEventsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityEventsService {
    @Autowired
    SecurityEventsRepo securityEventsRepo;

    public List<SecurityEventsDTO> getEventsLogg() {
        return securityEventsRepo.findAll();
    }

    public void saveLog(SecurityEventsDTO eventsDTO) {
        securityEventsRepo.save(eventsDTO);
    }
}
