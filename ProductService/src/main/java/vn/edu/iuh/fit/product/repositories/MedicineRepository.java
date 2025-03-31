package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, JpaSpecificationExecutor<Medicine> {

    @Query("SELECT COUNT(distinct(m.id)) FROM Medicine m JOIN m.categories c WHERE c.id = ?1")
    int countProductByCategory(Long id);

    @Query("SELECT DISTINCT m FROM Medicine m JOIN m.tags t WHERE t.id = ?1")
    Page<Medicine> findAllByTags_Id(Long tagId, Pageable pageable);

    //Hiển thị danh sách thuốc theo title của danh mục thuôc
    @Query("SELECT m FROM Medicine m JOIN m.categories c WHERE c.title = :title")
    List<Medicine> findAllByCategoryTitle(String title);
}
