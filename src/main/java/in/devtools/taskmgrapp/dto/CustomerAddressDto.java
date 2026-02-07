package in.devtools.taskmgrapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAddressDto {
    private String address;
    private String city;
    private String state;
    private String pincode;
}
