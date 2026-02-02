package in.devtools.taskmgrapp.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private String type;
    private Long customerId;
}
