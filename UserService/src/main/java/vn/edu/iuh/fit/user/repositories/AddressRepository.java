package vn.edu.iuh.fit.user.repositories;

import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.user.model.entity.Address;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{
}
