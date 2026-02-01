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
    public CustomerDto getCustomerById(Long Id) {
        return null;
    }

    @Override
    public void updateCustomer(CustomerDto customerDto) {

    }

}
