package in.devtools.taskmgrapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerKycDto {
    private Boolean panVerified;
    private LocalDateTime panVerifiedAt;
    private Boolean adharVerified;
    private LocalDateTime adharVerifiedAt;
}
