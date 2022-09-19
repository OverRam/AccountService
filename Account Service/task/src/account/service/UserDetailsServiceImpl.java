package account.service;

import account.HackedPassword;
import account.dataBase.UserRepository;
import account.errors.PasswordError;
import account.errors.UserExistError;
import account.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    HackedPassword hackedPassword;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepo.findByUsername(username).stream().findFirst();

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Username [%s] not found", username));
        }
    }

    public UserDetails loadUserByEmail(String email) {
        Optional<User> user = userRepo.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Email [%s] not found", email));
        }
    }

    public void changePassword(String newPassword, User u) {
        if (encoder.matches(newPassword, u.getPassword())) {
            throw new PasswordError("The passwords must be different!");
        }
        System.out.println("check encode");

        String encodePassword = encoder.encode(newPassword);
        checkIsHacked(encodePassword);
        u.setPassword(encodePassword);

        userRepo.save(u);
    }

    public void saveUser(User u) {
        String email = u.getEmail();
        Optional<User> user = userRepo.findByEmailIgnoreCase(email);

        if (user.isPresent()) {
            throw new UserExistError();
        }
        userRepo.save(u);
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public void checkIsHacked(String encodePassword) throws PasswordError {
        boolean isHacked = hackedPassword.getPasswordList()
                .stream().parallel().anyMatch(e -> encoder.matches(e, encodePassword));

        if (isHacked) {
            throw new PasswordError("The password is in the hacker's database!");
        }
    }


}
