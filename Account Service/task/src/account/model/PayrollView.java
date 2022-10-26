package account.model;

import lombok.Data;

@Data
public class PayrollView {
    private String name;
    private String lastname;
    private String period;
    private String salary;

    public String getSalary() {
        String dollar = salary.length() > 2 ? salary.substring(0, salary.length() - 2) : "0";
        String cent = salary.length() > 2 ? salary.substring(salary.length() - 2) : salary;

        return String.format("%s dollar(s) %s cent(s)", dollar, cent);
    }
}
