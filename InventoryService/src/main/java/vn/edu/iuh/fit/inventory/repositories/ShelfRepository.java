package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    @Query("SELECT s FROM Shelf s WHERE s.status = ?1")
    List<Shelf> findShelfByStatus(String status, Pageable pageable);
}
