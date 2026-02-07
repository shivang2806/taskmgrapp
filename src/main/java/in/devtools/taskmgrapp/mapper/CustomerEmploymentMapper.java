package in.devtools.taskmgrapp.mapper;

import in.devtools.taskmgrapp.dto.CustomerEmploymentDto;
import in.devtools.taskmgrapp.entity.CustomerEmployment;
import org.springframework.stereotype.Component;

@Component
public class CustomerEmploymentMapper {
    public CustomerEmploymentDto toDto(CustomerEmployment emp) {
        if (emp == null) return null;

        CustomerEmploymentDto dto = new CustomerEmploymentDto();
        dto.setCompanyName(emp.getCompanyName());
        dto.setSalary(emp.getSalary());
        dto.setLocation(emp.getLocation());
        dto.setExperience(emp.getExperience());

        return dto;
    }
}
