package account.model.DTO;

import account.model.Operation;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserStatusDTO {
    @NotBlank
    private String userEmail;
    @NotNull
    private Operation operation;

}
