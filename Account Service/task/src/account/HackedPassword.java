package account;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HackedPassword {
    List<String> passwordList = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch",
            "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust", "PasswordForApril",
            "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

    public List<String> getPasswordList() {
        return passwordList;
    }
}
