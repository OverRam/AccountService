package account.model.DTO;

import account.model.SecurityEvents;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "SecurityEventLog")
public class SecurityEventsDTO {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String date = LocalDateTime.now().toString();

    @NotNull
    @Enumerated(EnumType.STRING)
    private SecurityEvents action;

    @NotBlank
    private String subject; // The user who performed the action

    @NotBlank
    private String object;    //The object on which the action was performed

    @NotBlank
    @JsonProperty("patch")
    private String apiPatch;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SecurityEventsDTO that = (SecurityEventsDTO) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static SecurityEventsDTO factoryEvent(SecurityEvents action, String subject, String object, String apiPatch) {
        SecurityEventsDTO e = new SecurityEventsDTO();
        e.setAction(action);
        e.setSubject(subject);
        e.setObject(object);
        e.setApiPatch(apiPatch);

        return e;
    }
}
