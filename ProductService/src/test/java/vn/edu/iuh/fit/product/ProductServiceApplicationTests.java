package vn.edu.iuh.fit.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.repositories.MedicineRepository;
import vn.edu.iuh.fit.product.services.MedicineService;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MedicineRepository medicineRepository;

    @Test
    public void testFindAll() {
        List<Medicine> medicines = medicineRepository.findAll();
        // Export table word


    }


}
