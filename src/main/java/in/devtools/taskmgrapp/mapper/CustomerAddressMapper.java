package in.devtools.taskmgrapp.mapper;

import in.devtools.taskmgrapp.dto.CustomerAddressDto;
import in.devtools.taskmgrapp.entity.CustomerAddress;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressMapper {

    public CustomerAddressDto toDto(CustomerAddress address) {
        if (address == null) return null;

        CustomerAddressDto dto = new CustomerAddressDto();
        dto.setAddress(address.getAddress());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPincode(address.getPincode());

        return dto;
    }
}
