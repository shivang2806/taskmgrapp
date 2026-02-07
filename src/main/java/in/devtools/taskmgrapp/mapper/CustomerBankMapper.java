package in.devtools.taskmgrapp.mapper;

import in.devtools.taskmgrapp.dto.CustomerBankDto;
import in.devtools.taskmgrapp.entity.CustomerBank;
import org.springframework.stereotype.Component;

@Component
public class CustomerBankMapper {

    public CustomerBankDto toDto(CustomerBank bank) {
        if (bank == null) return null;

        CustomerBankDto dto = new CustomerBankDto();
        dto.setBankName(bank.getBankName());
        dto.setAccountHolderName(bank.getAccountHolderName());
        dto.setAccountNumber(bank.getAccountNumber());
        dto.setAccountType(bank.getAccountType());
        dto.setIfscCode(bank.getIfscCode());
        dto.setBranchName(bank.getBranchName());
        dto.setIsVerified(bank.getIsVerified());
        dto.setVerifiedAt(bank.getVerifiedAt());

        return dto;
    }
}
