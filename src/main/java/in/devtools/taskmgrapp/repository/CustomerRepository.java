package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    //
}
