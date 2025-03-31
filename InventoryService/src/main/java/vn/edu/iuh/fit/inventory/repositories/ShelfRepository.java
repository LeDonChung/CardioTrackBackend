package vn.edu.iuh.fit.inventory.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.inventory.models.entities.Shelf;

import java.util.List;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    //hiển thị thông tin của các shelf có capacity - totalProduct lớn hơn một giá trị nhập từ bàn phím
    @Query("select s from Shelf s where s.capacity - s.totalProduct > :threshold")
    List<Shelf> findShelfsWithCapacityGreaterThan(int threshold);

    //Cập nhật số lượng sản phẩm của shelf
    @Modifying
    @Query("update Shelf s set s.totalProduct = s.totalProduct + :quantity,s.notes = CONCAT('+ ', :quantity) where s.id = :id")
    void updateTotalProduct(Long id, int quantity);

    @Query("SELECT s FROM Shelf s WHERE s.location LIKE %?1%")
    Page<Shelf> findByLocation(String location, Pageable pageable);

    //Cập nhật số lượng sản phẩm của shelf khi đặt hàng
    @Modifying
    @Query("update Shelf s set s.totalProduct = s.totalProduct - :quantity,s.notes = CONCAT('- ', :quantity) where s.id = :id")
    void updateTotalProductWhenOrder(Long id, int quantity);

    @Query("SELECT s FROM Shelf s WHERE s.location like %?1%")
    Page<Shelf> findAllByLocation(String location, Pageable pageable);
}
