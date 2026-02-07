package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.CustomerBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBankRepository extends JpaRepository<CustomerBank, Long> {
    //
}
