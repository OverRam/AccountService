package account.controllers;

import account.model.DTO.PayrollViewDTO;
import account.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/api/empl/")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    PaymentService paymentService;

    @GetMapping("payment/{period}")
    ResponseEntity<PayrollViewDTO> getPaymentByPeriod(
            @NotNull(message = "You must be logged in!!!!") Authentication auth,
            @Valid @RequestBody @Pattern(regexp = "((1[0-2])|(0[0-9]))-\\d{4}",
                    message = "Wrong date!!") @PathVariable String period) {

        return new ResponseEntity<>(paymentService.getUserPayment(auth.getName(), period), HttpStatus.OK);
    }

    @GetMapping("payment")
    ResponseEntity<List<PayrollViewDTO>> getListPayroll(
            @NotNull(message = "You must be logged in!!!!") Authentication auth) {
        return new ResponseEntity<>(paymentService.getUserPaymentsList(auth.getName()), HttpStatus.OK);
    }

}
