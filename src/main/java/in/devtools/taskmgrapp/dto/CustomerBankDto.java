package in.devtools.taskmgrapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerBankDto {
    private String bankName;
    private String accountHolderName;
    private String accountNumber;
    private String accountType;
    private String ifscCode;
    private String branchName;
    private Boolean isVerified;
    private LocalDateTime verifiedAt;
}
