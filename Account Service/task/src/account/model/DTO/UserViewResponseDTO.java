package account.model.DTO;

import account.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserViewResponseDTO {
    private String name;
    private String lastname;
    private String email;

    public UserViewResponseDTO(User user) {
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
    }
}