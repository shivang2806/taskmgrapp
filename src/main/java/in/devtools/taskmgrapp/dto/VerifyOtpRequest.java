package in.devtools.taskmgrapp.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String type;
    private Long customerId;
    private Integer otp;
}
