package in.devtools.taskmgrapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String name;
    private String email;
    private Long mobile;
    private LocalDate dob;
    private BigDecimal monthlyIncome;
    private String custImage;
    private String status;
    private String adharNo;
    private String panNo;
    private Boolean emailVerified;
    private Boolean mobileVerified;
    private Boolean panVerified;
    private Boolean adharVerified;
    private LocalDateTime createdAt;


}
