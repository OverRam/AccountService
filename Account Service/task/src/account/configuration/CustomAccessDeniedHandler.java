package account.configuration;

import account.model.DTO.SecurityEventsDTO;
import account.model.SecurityEvents;
import account.service.SecurityEventsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Autowired
    SecurityEventsService eventsService;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        String subject = SecurityContextHolder.getContext().getAuthentication().getName();
        String patch = request.getServletPath();
        SecurityEventsDTO e = SecurityEventsDTO.factoryEvent(SecurityEvents.ACCESS_DENIED, subject, patch, patch);

        response.sendError(HttpServletResponse.SC_BAD_REQUEST, String.format("Access Denied for user %s!!!", subject));

        eventsService.saveLog(e);
        log.warn(e);
    }
}
