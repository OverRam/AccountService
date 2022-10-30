package account.service;

import account.Repository.UserRepository;
import account.exception.NoMatchInDatabase;
import account.model.DTO.UserRoleInfoDTO;
import account.model.DTO.UserViewResponseDTO;
import account.model.Role;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    public List<UserViewResponseDTO> findAllUser() {
        return userRepository.findAll().stream().map(UserViewResponseDTO::new).collect(Collectors.toList());
    }

    public void deleteUser(String email) {
        User u = findUserByEmail(email);
        if (u.getRoles().get(0) == Role.ROLE_ADMIN)
            throw new IllegalArgumentException("Can't remove ADMINISTRATOR role!");
        userRepository.delete(u);
    }

    public User changeUserRole(UserRoleInfoDTO newRoleInfo) {
        boolean isGrant = newRoleInfo.getOperation().equalsIgnoreCase("GRANT");

        //true if new role is in enum set
        boolean isCorrectRole = Arrays.stream(Role.values()).anyMatch(r -> r.name().equals(newRoleInfo.getRole()));
        if (!isCorrectRole)
            throw new IllegalArgumentException("Role not found!");

        User u = findUserByEmail(newRoleInfo.getEmail());

        boolean isAdminRole = Role.valueOf(newRoleInfo.getRole()) == Role.ROLE_ADMIN;

        //true if it is admin
        boolean isUserHaveAdminRole = u.getRoles().stream().anyMatch(r -> r == Role.ROLE_ADMIN);

        //GRANT
        if (isGrant) {
            // admin grant to user || user grant to admin
            if (isAdminRole && !isUserHaveAdminRole || !isAdminRole && isUserHaveAdminRole)
                throw new IllegalArgumentException("The user cannot combine administrative and business roles!");

            u.getRoles().add(Role.valueOf(newRoleInfo.getRole()));
            //REMOVE
        } else {
            if (isUserHaveAdminRole)
                throw new IllegalArgumentException("Can't remove ADMINISTRATOR role!");

            //true if user does not have the role
            boolean isUserHaveThatRole = u.getRoles().stream().anyMatch(r -> r.name().equals(newRoleInfo.getRole()));

            if (!isUserHaveThatRole)
                throw new IllegalArgumentException("The user does not have a role!");

            if (u.getRoles().size() == 1)
                throw new IllegalArgumentException("The user must have at least one role!");

            u.getRoles().remove(Role.valueOf(newRoleInfo.getRole()));
        }

        userRepository.save(u);
        return u;
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NoMatchInDatabase("User not found!"));
    }

}
