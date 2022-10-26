package account.model.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class PaymentDto {

    @Email(regexp = ".*@acme\\.com", message = "Wrong email, must end with @acme.com")
    private String email;

    @Pattern(regexp = "((1[0-2])|(0[0-9]))-\\d{4}", message = "Wrong date!!")
    private String period;

    @Min(value = 0, message = "Must be positive number!")
    private Long salary;
}
