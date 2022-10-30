package account.model.DTO;

import account.model.Role;
import account.model.User;
import lombok.Data;

import java.util.List;

@Data
public class UserViewResponseDTO {
    private long id;
    private String name;
    private String lastname;
    private String email;
    private List<Role> roles;

    public UserViewResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}