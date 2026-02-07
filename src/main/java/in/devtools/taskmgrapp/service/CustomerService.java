package in.devtools.taskmgrapp.service;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.dto.StoreCustomerDto;
import in.devtools.taskmgrapp.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<CustomerDto> getAllCustomer();
    void createCustomer(StoreCustomerDto storeCustomerDto);
    CustomerDto getCustomerById(Long id);
    void updateCustomer(CustomerDto CustomerDto);
    void sendVerificationOtp(String type, Long customerId);
    void verifyOtp(String type, Long customerId, Integer otp);
    void saveAddressDetails(Customer customer);
    void saveEmploymentDetails(Customer customer);
    void saveKycDetails(Customer customer);
    void saveBankDetails(Customer customer);

}
