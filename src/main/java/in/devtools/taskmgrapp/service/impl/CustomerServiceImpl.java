package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.dto.StoreCustomerDto;
import in.devtools.taskmgrapp.dto.TaskDto;
import in.devtools.taskmgrapp.entity.Customer;
import in.devtools.taskmgrapp.entity.Task;
import in.devtools.taskmgrapp.repository.CustomerRepository;
import in.devtools.taskmgrapp.repository.TaskRepository;
import in.devtools.taskmgrapp.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.nio.file.Paths;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    @Override
    public List<CustomerDto> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        return customers.stream().map((customer)->modelMapper.map(customer, CustomerDto.class)).collect(Collectors.toList());
    }

    @Override
    public void createCustomer(StoreCustomerDto storeCustomerDto) {
        Customer customer = new Customer();

        customer.setName(storeCustomerDto.getName());
        customer.setEmail(storeCustomerDto.getEmail());
        customer.setMobile(storeCustomerDto.getMobile());
        customer.setDob(storeCustomerDto.getDob());
        customer.setMonthlyIncome(storeCustomerDto.getMonthlyIncome());

        // ✅ IMAGE HANDLING
        MultipartFile image = storeCustomerDto.getCustImage();

        if (image != null && !image.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/uploads/customers/";
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            try {
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, image.getBytes());

                // ✅ store path in DB
                customer.setCustImage(uploadDir + fileName);

            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        customerRepository.save(customer);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return modelMapper.map(customer, CustomerDto.class);

    }


    @Override
    public void updateCustomer(CustomerDto customerDto) {

    }

    @Override
    public void sendVerificationOtp(String type, Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

//        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        Integer otp = 123456;

        if ("email".equalsIgnoreCase(type)) {
            // TODO: send email OTP
            System.out.println("Sending EMAIL OTP " + otp + " to " + customer.getEmail());
        }
        else if ("mobile".equalsIgnoreCase(type)) {
            // TODO: send SMS OTP
            System.out.println("Sending MOBILE OTP " + otp + " to " + customer.getMobile());
        }
        else {
            throw new IllegalArgumentException("Invalid verification type");
        }

        customer.setOtp(otp);
        customerRepository.save(customer);

        // TODO: save OTP to DB / cache with expiry
    }

    @Override
    public void verifyOtp(String type, Long customerId, Integer otp) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (!Objects.equals(customer.getOtp(), otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }

        if ("email".equalsIgnoreCase(type) ) {
            customer.setEmailVerified(true);
        }
        else if ("mobile".equalsIgnoreCase(type)) {
            customer.setMobileVerified(true);
        }
        else {
            throw new IllegalArgumentException("Invalid verification type");
        }
        customer.setOtp(null);

        customerRepository.save(customer);

    }


}
