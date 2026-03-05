package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findAllByAssignedUserIsNull(Pageable pageable);

    @Query("SELECT c.id FROM Customer c WHERE c.assignedUser IS NULL")
    List<Long> findAllUnassignedIds();

    @Modifying
    @Transactional
    @Query(value = "UPDATE customers SET user_id = :userId WHERE id = :customerId", nativeQuery = true)
    void assignUser(@Param("customerId") Long customerId, @Param("userId") Long userId);
}
