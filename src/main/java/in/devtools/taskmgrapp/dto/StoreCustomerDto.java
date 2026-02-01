package in.devtools.taskmgrapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreCustomerDto {

    @NotBlank(message = "Name should not be empty")
    private String name;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Mobile should not be null")
    private Long mobile;

    @NotNull(message = "Date of birth should not be null")
    private LocalDate dob;

    @NotNull(message = "Monthly income should not be null")
    private BigDecimal monthlyIncome;

    // optional
    private MultipartFile custImage;

    // getters & setters
}
