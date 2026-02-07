package in.devtools.taskmgrapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerProfileDto {
    private CustomerDto customer;
    private CustomerAddressDto address;
    private CustomerBankDto bank;
    private CustomerEmploymentDto employment;
    private CustomerKycDto kyc;
}
