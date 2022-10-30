package account.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRoleInfoDTO {
    @NotBlank(message = "Cant be empty")
    @JsonProperty(value = "user")
    private String email;
    @NotBlank(message = "Cant be empty")
    private String role;
    @NotBlank(message = "Cant be empty")
    private String operation;
}
