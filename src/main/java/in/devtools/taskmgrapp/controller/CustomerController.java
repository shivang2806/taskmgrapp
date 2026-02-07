package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.dto.*;
import in.devtools.taskmgrapp.service.CustomerProfileService;
import in.devtools.taskmgrapp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;
    private CustomerProfileService customerProfileService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/customer-list")
    public String getAllCustomer(Model model)
    {
        List<CustomerDto> customers =  customerService.getAllCustomer();
        model.addAttribute("customers", customers);

        return "customers/customerList";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create-customer")
    public String createCustomer(Model model)
    {
        StoreCustomerDto storeCustomerDto = new StoreCustomerDto();
        model.addAttribute("customer", storeCustomerDto);
        return "customers/customerCreate";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/customer-store")
    public String storeCustomer(
            @Valid @ModelAttribute("customer") StoreCustomerDto storeCustomerDto,
            BindingResult result,
            Model model
    )
    {
        if (result.hasErrors()) {
            return "customers/customerCreate";
        }

        customerService.createCustomer(storeCustomerDto);
        return "redirect:/customer-list";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/customer-profile/{id}")
    public String getCustomerProfile(
            @PathVariable("id") Long id,
            Model model
    ) {
        CustomerProfileDto profile = customerProfileService.getCustomerProfile(id);

        model.addAttribute("customer", profile.getCustomer());
        model.addAttribute("address", profile.getAddress());
        model.addAttribute("bank", profile.getBank());
        model.addAttribute("employment", profile.getEmployment());
        model.addAttribute("kyc", profile.getKyc());

        return "customers/profile";
    }

    @PostMapping("/send-customer-verification-otp")
    @ResponseBody
    public ResponseEntity<?> sendCustomerVerificationOtp(@RequestBody OtpRequest request) {
        customerService.sendVerificationOtp(
                request.getType(),
                request.getCustomerId()
        );

        return ResponseEntity.ok(
                Map.of("message", "OTP sent successfully")
        );
    }

    @PostMapping("/customer-verify-otp")
    @ResponseBody
    public ResponseEntity<?> customerVerifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            customerService.verifyOtp(
                    request.getType(),
                    request.getCustomerId(),
                    request.getOtp()
            );
            return ResponseEntity.ok(Map.of("message", "OTP Verified successfully"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", ex.getMessage())
            );
        }
    }


}
