package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.CustomerKyc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerKycRepository extends JpaRepository<CustomerKyc, Long> {
}
