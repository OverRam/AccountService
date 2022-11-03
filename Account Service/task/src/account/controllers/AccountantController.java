package account.controllers;

import account.model.DTO.PaymentDto;
import account.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/acct/")
public class AccountantController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("payments")
    public ResponseEntity<LinkedHashMap<String, String>> uploadsPayrolls(
            @RequestBody @Valid List<@Valid PaymentDto> paymentsList) {

        paymentService.addListPayrolls(paymentsList);

        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        lhm.put("status", "Added successfully!");
        return new ResponseEntity<>(lhm, HttpStatus.OK);
    }

    @PutMapping("payments")
    public ResponseEntity<LinkedHashMap<String, String>> changeSalaryUser(@RequestBody @Valid PaymentDto payroll) {
        paymentService.updatePayroll(payroll);

        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        lhm.put("status", "Added successfully!");
        return new ResponseEntity<>(lhm, HttpStatus.OK);
    }

}
