package in.devtools.taskmgrapp.mapper;

import in.devtools.taskmgrapp.dto.CustomerKycDto;
import in.devtools.taskmgrapp.entity.CustomerKyc;
import org.springframework.stereotype.Component;

@Component
public class CustomerKycMapper {

    public CustomerKycDto toDto(CustomerKyc kyc) {
        if (kyc == null) return null;

        CustomerKycDto dto = new CustomerKycDto();
        dto.setPanVerified(kyc.getPanVerified());
        dto.setPanVerifiedAt(kyc.getPanVerifiedAt());
        dto.setAdharVerified(kyc.getAdharVerified());
        dto.setAdharVerifiedAt(kyc.getAdharVerifiedAt());

        return dto;
    }

}
