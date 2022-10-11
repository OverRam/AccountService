package account.controllers;

import account.model.EmployeeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/api/acct")
public class AccountantController {

    @PostMapping("/payments")
    ResponseEntity<LinkedHashMap<String, String>> uploadsPayrolls(@Valid @RequestBody List<EmployeeDto> empList) {


        LinkedHashMap<String, String> lhm = new LinkedHashMap<>();
        lhm.put("status", "Added successfully!");

        return new ResponseEntity<>(lhm, HttpStatus.OK);
    }

    @PutMapping("/payments")
    String changeSalaryUser() {
        return "";
    }

    @GetMapping("/payments")
    String giveAccessToPayrollEmployee() {
        return "";
    }
}
