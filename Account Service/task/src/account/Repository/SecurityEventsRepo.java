package account.Repository;

import account.model.DTO.SecurityEventsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityEventsRepo extends JpaRepository<SecurityEventsDTO, Long> {
}
