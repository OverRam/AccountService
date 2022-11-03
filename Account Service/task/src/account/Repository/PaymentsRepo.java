package account.Repository;

import account.model.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentsRepo extends JpaRepository<Payroll, String> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update Payroll p SET p.salary = ?1 " +
            "WHERE " +
            "p.user_id = ?2 " +
            "AND " +
            "p.period = ?3", nativeQuery = true)
    void updatePaymentUserByPeriodAndUserId(Long salaryNewValue, Long id, String paymentPeriod);

    @Query(value = "select * from Payroll where user_id = ?1 and period =?2", nativeQuery = true)
    Optional<Payroll> findByUserIdAndPeriod(Long id, String period);

    @Query(value = "select * from Payroll where user_id = ?1", nativeQuery = true)
    Optional<List<Payroll>> findByUserId(Long id);
}
