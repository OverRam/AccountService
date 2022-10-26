package account.model.mapper;

import account.model.DTO.PaymentDto;
import account.model.Payroll;
import account.model.User;
import org.springframework.stereotype.Component;

@Component
public class MapperPaymentsInfo {
    public Payroll toUserPaymentsInfo(PaymentDto paymentDto) {
        User u = new User();
        u.setEmail(paymentDto.getEmail());

        Payroll p = new Payroll();
        p.setUserEmail(u);
        p.setSalary(paymentDto.getSalary());
        p.setPeriod(paymentDto.getPeriod());
        return p;
    }
}
