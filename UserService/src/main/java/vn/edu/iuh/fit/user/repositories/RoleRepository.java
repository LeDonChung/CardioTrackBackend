package vn.edu.iuh.fit.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.user.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    public Role findByCode(String code);
}
