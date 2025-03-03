package vn.edu.iuh.fit.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.user.model.entity.AddressDetail;

import java.util.List;

@Repository
public interface AddressDetailRepository extends JpaRepository<AddressDetail, Long> {
    List<AddressDetail> findByUserId(Long userId);
}
