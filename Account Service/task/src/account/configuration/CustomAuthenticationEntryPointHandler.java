package account.configuration;

import account.Repository.SecurityEventsRepo;
import account.model.DTO.SecurityEventsDTO;
import account.model.SecurityEvents;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Autowired
    SecurityEventsRepo securityEventsRepo;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        SecurityEventsDTO e = new SecurityEventsDTO();
        e.setAction(SecurityEvents.LOGIN_FAILED);
        e.setSubject(auth.getName());
        e.setObject(request.getPathInfo());
        e.setApiPatch(request.getPathInfo());

        securityEventsRepo.save(e);
        log.info(e);
        resolver.resolveException(request, response, null, authException);
    }
}
