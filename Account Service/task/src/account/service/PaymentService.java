package account.service;

import account.Repository.PaymentsRepo;
import account.exception.NoMatchInDatabase;
import account.model.DTO.PaymentDto;
import account.model.Payroll;
import account.model.PayrollView;
import account.model.User;
import account.model.mapper.MapperPaymentsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentsRepo repoPayment;
    @Autowired
    private MapperPaymentsInfo mapper;
    @Autowired
    private UserService userService;

    @Transactional
    public void addListPayrolls(List<PaymentDto> paymentDto) {
        List<Payroll> payrollList = paymentDto.stream()
                .map(mapper::toUserPaymentsInfo)
                .collect(Collectors.toList());
        repoPayment.saveAll(payrollList);
    }

    @Transactional()
    public void updatePayroll(PaymentDto paymentDto) {
        repoPayment.updatePaymentUserByPeriodAndEmail(paymentDto.getSalary(), paymentDto.getEmail(), paymentDto.getPeriod());
    }

    public PayrollView getUserPayment(String userEmail, String period) {

        User u = (User) userService.loadUserByUsername(userEmail);
        Payroll p = repoPayment.findByEmailAndPeriod(userEmail, period)
                .orElseThrow(() -> new NoMatchInDatabase(String
                        .format("No data matching this set was found: %s and %s", userEmail, period)));

        return mapToPayrollView(u, p);
    }

    public List<PayrollView> getUserPaymentsList(String userEmail) {
        List<Payroll> payrollList = repoPayment.findByEmail(userEmail)
                .orElseThrow(() -> new NoMatchInDatabase(String
                        .format("No data matching this email was found: %s", userEmail)));

        User u = (User) userService.loadUserByUsername(userEmail);
        return payrollList.stream().map(p -> mapToPayrollView(u, p)).collect(Collectors.toList());
    }

    private PayrollView mapToPayrollView(User u, Payroll p) {
        PayrollView pv = new PayrollView();
        pv.setSalary(p.getSalary().toString());
        pv.setName(u.getName());
        pv.setLastname(u.getLastname());
        pv.setPeriod(p.getPeriod());
        return pv;
    }
}
