package vn.edu.iuh.fit.product.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.model.entities.Brand;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.model.entities.Medicine;
import vn.edu.iuh.fit.product.services.BrandService;
import vn.edu.iuh.fit.product.services.CategoryService;
import vn.edu.iuh.fit.product.services.MedicineService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/v1/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryService categoryService;

    //Get all medicines
    @GetMapping("/getAllMedicines")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Medicine>> getAllMedicines() {
        List<Medicine> medicines = medicineService.getAllMedicines();
        return ResponseEntity.ok(medicines);
    }

    //update status by id
    @PatchMapping("/updateStatusById/{id}/{status}")
    public ResponseEntity<String> updateStatusById(@PathVariable Long id, @PathVariable int status) {
        medicineService.updateStatusById(id, status);
        return ResponseEntity.ok("Update status success");
    }

    //add - update medicine
    @PostMapping("/addMedicine")
    @PutMapping("/addMedicine/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addMedicine(@PathVariable(required = false) Long id, @RequestBody Map<String, Object> requestData) {
        Medicine medicine = objectMapper.convertValue(requestData, Medicine.class);

        if (id != null) {
            Medicine existingMedicine = medicineService.getMedicineById(id);
            if (existingMedicine == null) {
                System.out.println("Medicine with ID " + id + " not found!");
                return;
            }
            medicine.setId(id);
        }

        Long categoryId = (requestData.get("category_id") != null) ? Long.valueOf(requestData.get("category_id").toString()) : null;
        Long brandId = (requestData.get("brand_id") != null) ? Long.valueOf(requestData.get("brand_id").toString()) : null;

        if (categoryId != null) {
            Category category = categoryService.getCategoryById(categoryId);
            if (category != null) {
                medicine.setCategory(category);
            } else {
                System.out.println("Category with ID " + categoryId + " not found!");
            }
        }

        if (brandId != null) {
            Brand brand = brandService.getBrandById(brandId);
            if (brand != null) {
                medicine.setBrand(brand);
            } else {
                System.out.println("Brand with ID " + brandId + " not found!");
            }
        }
        System.out.println("Medicine: " + medicine);
        medicineService.addMedicine(medicine);
    }

    //find by name
    @GetMapping("/findMedicineByName/{name}")
    public ResponseEntity<List<Medicine>> findMedicineByName(@PathVariable String name) {
        List<Medicine> medicines = medicineService.findMedicineByName(name);
        System.out.println("Medicine of "+ name + ": " + medicines);
        return ResponseEntity.ok(medicines);
    }

    //find by brand id
    @GetMapping("/getAllMedicinesByBrandId/{brandId}")
    public ResponseEntity<List<Medicine>> getAllMedicinesByBrandId(@PathVariable Long brandId) {
        List<Medicine> medicines = medicineService.getAllMedicinesByBrandId(brandId);
        System.out.println("Medicine of "+ brandId + ": " + medicines);
        return ResponseEntity.ok(medicines);
    }

    //find by description
    @GetMapping("/findMedicineByDes/{des}")
    public ResponseEntity<List<Medicine>> findMedicineByDes(@PathVariable String des) {
        List<Medicine> medicines = medicineService.findMedicineByDes(des);
        System.out.println("Medicine of "+ des + ": " + medicines);
        return ResponseEntity.ok(medicines);
    }

    //find by description short
    @GetMapping("/findMedicineByDesShort/{desShort}")
    public ResponseEntity<List<Medicine>> findMedicineByDesShort(@PathVariable String desShort) {
        List<Medicine> medicines = medicineService.findMedicineByDesShort(desShort);
        System.out.println("Medicine of "+ desShort + ": " + medicines);
        return ResponseEntity.ok(medicines);
    }
}
