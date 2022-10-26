package account.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayrollViewTest {

    @Test
    @DisplayName("Check 2 digit dollar and cents")
    void checkFormatWithDollarsAndCents() {
        PayrollView p = new PayrollView();
        p.setSalary("2133");
        Assertions.assertEquals("21 dollar(s) 33 cent(s)", p.getSalary());
    }


    @Test
    @DisplayName("Check 1 digit dollar and cents")
    void checkFormatWithOneDollarDigitAndCents() {
        PayrollView p = new PayrollView();
        p.setSalary("133");
        Assertions.assertEquals("1 dollar(s) 33 cent(s)", p.getSalary());
    }

    @Test
    @DisplayName("Check 2 digit cent")
    void checkFormatWithCents() {
        PayrollView p = new PayrollView();
        p.setSalary("33");
        Assertions.assertEquals("0 dollar(s) 33 cent(s)", p.getSalary());
    }

    @Test
    @DisplayName("Check 1 digit cent")
    void checkFormatWithOneDigitCents() {
        PayrollView p = new PayrollView();
        p.setSalary("3");
        Assertions.assertEquals("0 dollar(s) 3 cent(s)", p.getSalary());
    }

    @Test
    @DisplayName("Check many digits dollar")
    void checkFormatWithManyDigitsDollars() {
        PayrollView p = new PayrollView();
        p.setSalary("123456789");
        Assertions.assertEquals("1234567 dollar(s) 89 cent(s)", p.getSalary());
    }
}