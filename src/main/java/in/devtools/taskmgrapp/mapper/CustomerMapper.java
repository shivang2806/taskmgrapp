package in.devtools.taskmgrapp.mapper;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer customer) {
        if (customer == null) {
            return null;
        }

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setMobile(customer.getMobile());
        dto.setEmailVerified(customer.getEmailVerified());
        dto.setMobileVerified(customer.getMobileVerified());
        dto.setCreatedAt(customer.getCreatedAt());

        return dto;
    }
}
