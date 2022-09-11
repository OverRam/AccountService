package account.service;

import account.dataBase.UserRepository;
import account.errors.UserExistError;
import account.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepo;

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

}
