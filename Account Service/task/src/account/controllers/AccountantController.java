package account.controllers;

import account.model.DTO.PaymentDto;
import account.model.PayrollView;
import account.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/acct")
public class AccountantController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payments")
    ResponseEntity<LinkedHashMap<String, String>> uploadsPayrolls(@RequestBody @Valid List<@Valid PaymentDto> paymentsList) {
        paymentService.addListPayrolls(paymentsList);

        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        lhm.put("status", "Added successfully!");
        return new ResponseEntity<>(lhm, HttpStatus.OK);
    }

    @PutMapping("/payments")
    ResponseEntity<LinkedHashMap<String, String>> changeSalaryUser(@RequestBody @Valid PaymentDto payroll) {
        paymentService.updatePayroll(payroll);

        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        lhm.put("status", "Added successfully!");
        return new ResponseEntity<>(lhm, HttpStatus.OK);
    }

    @GetMapping("/payments/{period}")
    ResponseEntity<PayrollView> getPaymentByPeriod(
            @NotNull(message = "You must be logged in!!!!") Authentication auth,
            @Valid @RequestBody @Pattern(regexp = "((1[0-2])|(0[0-9]))-\\d{4}",
                    message = "Wrong date!!") @PathVariable String period) {

        return new ResponseEntity<>(paymentService.getUserPayment(auth.getName(), period), HttpStatus.OK);
    }

    @GetMapping("/payments")
    ResponseEntity<List<PayrollView>> getPaymentByPeriod(
            @NotNull(message = "You must be logged in!!!!") Authentication auth) {
        return new ResponseEntity<>(paymentService.getUserPaymentsList(auth.getName()), HttpStatus.OK);
    }
}
