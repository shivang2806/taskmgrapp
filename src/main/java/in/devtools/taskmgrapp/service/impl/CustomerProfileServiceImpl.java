package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.CustomerProfileDto;
import in.devtools.taskmgrapp.entity.Customer;
import in.devtools.taskmgrapp.mapper.*;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import in.devtools.taskmgrapp.service.CustomerProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerProfileServiceImpl implements CustomerProfileService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerAddressMapper addressMapper;
    private final CustomerBankMapper bankMapper;
    private final CustomerEmploymentMapper employmentMapper;
    private final CustomerKycMapper kycMapper;

    @Override
    public CustomerProfileDto getCustomerProfile(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerProfileDto profile = new CustomerProfileDto();

        profile.setCustomer(customerMapper.toDto(customer));
        profile.setAddress(addressMapper.toDto(customer.getCustomerAddress()));
        profile.setBank(bankMapper.toDto(customer.getCustomerBank()));
        profile.setEmployment(
                employmentMapper.toDto(customer.getCustomerEmployment())
        );
        profile.setKyc(
                kycMapper.toDto(customer.getCustomerKyc())
        );

        return profile;
    }
}
