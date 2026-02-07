package in.devtools.taskmgrapp.service.impl;

import in.devtools.taskmgrapp.dto.CustomerDto;
import in.devtools.taskmgrapp.dto.StoreCustomerDto;
import in.devtools.taskmgrapp.entity.*;
import in.devtools.taskmgrapp.repository.*;
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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private CustomerAddressRepository customerAddressRepository;
    private CustomerEmploymentRepository customerEmploymentRepository;
    private CustomerKycRepository customerKycRepository;
    private CustomerBankRepository customerBankRepository;
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

        saveAddressDetails(customer);
        saveEmploymentDetails(customer);
        saveKycDetails(customer);
        saveBankDetails(customer);

    }

    @Override
    public void saveAddressDetails(Customer customer) {
        CustomerAddress customerAddress = new CustomerAddress();

        Random random = new Random();

        String[] cities = {"Delhi", "Mumbai", "Bengaluru", "Chennai", "Hyderabad"};
        String[] states = {"Delhi", "Maharashtra", "Karnataka", "Tamil Nadu", "Telangana"};

        int index = random.nextInt(cities.length);

        customerAddress.setCustomer(customer); // ✅ correct JPA relation
        customerAddress.setAddress("House No. " + (100 + random.nextInt(900)) + ", Street " + (1 + random.nextInt(50)));
        customerAddress.setCity(cities[index]);
        customerAddress.setState(states[index]);
        customerAddress.setPincode(String.valueOf(100000 + random.nextInt(900000)));

        customerAddressRepository.save(customerAddress);
    }

    @Override
    public void saveEmploymentDetails(Customer customer) {
        CustomerEmployment customerEmployment = new CustomerEmployment();

        Random random = new Random();

        String[] companyName = {"Google", "Microsoft", "Amazon", "Apple", "Netflix"};
        String[] states = {"Delhi", "Maharashtra", "Karnataka", "Tamil Nadu", "Telangana"};

        int index = random.nextInt(companyName.length);

        customerEmployment.setCustomer(customer); // ✅ correct JPA relation
        customerEmployment.setCompanyName(companyName[index]);
        customerEmployment.setExperience(index);
        customerEmployment.setLocation(states[index]);
        customerEmployment.setSalary(String.valueOf(100000 + random.nextInt(900000)));

        customerEmploymentRepository.save(customerEmployment);
    }

    @Override
    public void saveKycDetails(Customer customer) {

        CustomerKyc customerKyc = new CustomerKyc();
        Random random = new Random();

        String[] aadharList = {
                "827381729485", "918273648392", "918273917384",
                "617283949181", "918291829182"
        };

        String[] panList = {
                "KAJSYD0283H", "MJAHST7394H", "AKSYC9274D",
                "PAYSH7394C", "JAYCH8173K"
        };

        int index = random.nextInt(aadharList.length);

        // 🔗 sync BOTH sides of relation
        customerKyc.setCustomer(customer);
        customer.setCustomerKyc(customerKyc);

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Aadhaar JSON
            Map<String, String> aadharJson = new HashMap<>();
            aadharJson.put("aadhar_no", aadharList[index]);
            aadharJson.put("father_name", "test father");
            aadharJson.put("dob", "1997-12-28");

            customerKyc.setAdharDetails(mapper.writeValueAsString(aadharJson));
            customerKyc.setAdharVerified(true);
            customerKyc.setAdharVerifiedAt(LocalDateTime.now());

            // PAN JSON
            Map<String, String> panJson = new HashMap<>();
            panJson.put("pan_no", panList[index]);
            panJson.put("father_name", "test father");
            panJson.put("dob", "1997-12-28");

            customerKyc.setPanDetails(mapper.writeValueAsString(panJson));
            customerKyc.setPanVerified(true);
            customerKyc.setPanVerifiedAt(LocalDateTime.now());

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate KYC JSON", e);
        }

        // Keep customer table in sync
        customer.setAdharNo(aadharList[index]);
        customer.setAdharVerified(true);
        customer.setPanNo(panList[index]);
        customer.setPanVerified(true);

        // ✅ Cascade saves CustomerKyc
        customerRepository.save(customer);
    }

    @Override
    public void saveBankDetails(Customer customer) {

        CustomerBank customerBank = new CustomerBank();
        Random random = new Random();

        String[] bankNames = {"HDFC Bank", "ICICI Bank", "Axis Bank", "SBI", "Kotak Bank"};
        String[] branches = {"Connaught Place", "MG Road", "Indiranagar", "Bandra", "Salt Lake"};
        String[] accountTypes = {"SAVINGS", "CURRENT"};

        int index = random.nextInt(bankNames.length);

        // 🔗 JPA relation
        customerBank.setCustomer(customer);
        customer.setCustomerBank(customerBank); // ✅ important if bidirectional

        customerBank.setBankName(bankNames[index]);
        customerBank.setAccountHolderName(customer.getName());
        customerBank.setAccountNumber(
                String.valueOf(100000000000L + random.nextInt(900000000))
        );
        customerBank.setAccountType("SAVINGS");
        customerBank.setIfscCode("HDFC0" + (100000 + random.nextInt(900000)));
        customerBank.setBranchName(branches[index]);

        customerBank.setIsVerified(true);
        customerBank.setVerifiedAt(LocalDateTime.now());

        // ✅ cascade will save bank details
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
