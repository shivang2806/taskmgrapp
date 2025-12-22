package in.devtools.taskmgrapp.repository;

import in.devtools.taskmgrapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
