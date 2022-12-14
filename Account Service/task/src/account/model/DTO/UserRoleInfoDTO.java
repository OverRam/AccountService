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

    public static UserRoleInfoDTO getNewInstance(UserRoleInfoDTO dto) {
        UserRoleInfoDTO u = new UserRoleInfoDTO();
        u.setRole(dto.getRole());
        u.setEmail(dto.getEmail());
        u.setOperation(dto.getOperation());
        return u;
    }

}
