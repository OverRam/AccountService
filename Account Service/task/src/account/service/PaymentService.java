package account.service;

import account.Repository.PaymentsRepo;
import account.exception.NoMatchInDatabase;
import account.model.DTO.PaymentDto;
import account.model.DTO.PayrollViewDTO;
import account.model.Payroll;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {
    @Autowired
    private PaymentsRepo repoPayment;
    @Autowired
    private UserService userService;

    @Transactional
    public void addListPayrolls(List<PaymentDto> paymentDto) {
        List<Payroll> payrollList = paymentDto.stream()
                .map(this::toUserPaymentsInfo)
                .collect(Collectors.toList());

        repoPayment.saveAll(payrollList);

    }

    @Transactional()
    public void updatePayroll(PaymentDto paymentDto) {
        User u = userService.loadByEmail(paymentDto.getEmail());

        repoPayment.updatePaymentUserByPeriodAndUserId(paymentDto.getSalary(),
                u.getId(), paymentDto.getPeriod());
    }

    public PayrollViewDTO getUserPayment(String userEmail, String period) {
        User u = userService.loadByEmail(userEmail);

        Payroll p = repoPayment.findByUserIdAndPeriod(u.getId(), period)
                .orElseThrow(() -> new NoMatchInDatabase(String
                        .format("No data matching this set was found: %s and %s", userEmail, period)));

        return mapToPayrollView(u, p);
    }

    public List<PayrollViewDTO> getUserPaymentsList(String userEmail) {
        User u = userService.loadByEmail(userEmail);

        List<Payroll> payrollList = repoPayment.findByUserId(u.getId())
                .orElseThrow(() -> new NoMatchInDatabase(String
                        .format("No data matching this email was found: %s", userEmail)));


        return payrollList.stream().map(p -> mapToPayrollView(u, p)).collect(Collectors.toList());
    }

    private PayrollViewDTO mapToPayrollView(User u, Payroll p) {
        PayrollViewDTO pv = new PayrollViewDTO();
        pv.setSalary(p.getSalary().toString());
        pv.setName(u.getName());
        pv.setLastname(u.getLastname());
        pv.setPeriod(p.getPeriod());
        return pv;
    }

    public Payroll toUserPaymentsInfo(PaymentDto paymentDto) {
        User u = userService.loadByEmail(paymentDto.getEmail());

        Payroll p = new Payroll();
        p.setUserId(u);
        p.setSalary(paymentDto.getSalary());
        p.setPeriod(paymentDto.getPeriod());
        return p;
    }
}
