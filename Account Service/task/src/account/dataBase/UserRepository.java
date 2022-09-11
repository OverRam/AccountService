package account.dataBase;

import account.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailIgnoreCase(String email);


    @Override
    <S extends User> S save(S entity);
}