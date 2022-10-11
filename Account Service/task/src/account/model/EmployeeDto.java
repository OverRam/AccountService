package account.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class EmployeeDto {
    @NotBlank
    private String employee;

    @Pattern(regexp = "\\d{2}-\\d{4}", message = "Wrong date!!")
    private String period;

    @Min(value = 0, message = "Must be positive number!")
    private Long salary;

}
