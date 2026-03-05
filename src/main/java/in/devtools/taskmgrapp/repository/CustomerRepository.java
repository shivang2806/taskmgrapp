package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAllByAssignedUserIsNull();
    //
}
