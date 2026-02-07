package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long>  {
    //
}
