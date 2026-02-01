package in.devtools.taskmgrapp.controller;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.dto.StoreCustomerDto;
import in.devtools.taskmgrapp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

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

}
