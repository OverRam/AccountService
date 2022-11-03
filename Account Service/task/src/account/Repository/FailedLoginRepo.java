package account.Repository;

import account.model.FailedLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FailedLoginRepo extends JpaRepository<FailedLogin, Long> {
    Optional<FailedLogin> findByUserId(Long userId);
}
