package in.devtools.taskmgrapp.service;

import in.devtools.taskmgrapp.dto.CustomerProfileDto;

public interface CustomerProfileService {
    CustomerProfileDto getCustomerProfile(Long id);
}
