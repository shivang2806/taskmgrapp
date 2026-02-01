package in.devtools.taskmgrapp.service;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.dto.StoreCustomerDto;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomer();
    void createCustomer(StoreCustomerDto storeCustomerDto);
    CustomerDto getCustomerById(Long Id);
    void updateCustomer(CustomerDto CustomerDto);

}
