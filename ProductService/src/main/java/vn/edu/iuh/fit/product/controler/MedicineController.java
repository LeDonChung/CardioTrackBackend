package vn.edu.iuh.fit.product.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.product.model.entities.Brand;
import vn.edu.iuh.fit.product.model.entities.Category;
import vn.edu.iuh.fit.product.model.entities.Medicine;
import vn.edu.iuh.fit.product.services.CategoryService;
import vn.edu.iuh.fit.product.services.MedicineService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;
    @Autowired
    private CategoryService categoryService;

    //Get all medicines
    @GetMapping("/getAllMedicines")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addMedicine(@RequestBody Map<String, Object> requestData) {
        try {
            // Chuyển đổi dữ liệu JSON thành object Medicine
            ObjectMapper objectMapper = new ObjectMapper();
            Medicine medicine = objectMapper.convertValue(requestData, Medicine.class);

            // Xử lý danh sách category
            List<Long> categoryIds = (List<Long>) requestData.get("category_ids");
            if (categoryIds != null) {
                List<Category> categories = categoryIds.stream()
                        .map(categoryService::getCategoryById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                medicine.setCategory((Category) categories);
            }

            // Xử lý brand
            Long brandId = (requestData.get("brand_id") != null) ? Long.valueOf(requestData.get("brand_id").toString()) : null;
            if (brandId != null) {
                Brand brand = brandService.getBrandById(brandId);
                medicine.setBrand(brand);
            }


            // Xử lý danh sách images
            List<Long> imageIds = (List<Long>) requestData.get("image_ids");
            if (imageIds != null) {
                List<Image> images = imageIds.stream()
                        .map(imageService::getImageById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                medicine.setImages(images);
            }

            // Xử lý danh sách specifications
            List<Long> specificationIds = (List<Long>) requestData.get("specification_ids");
            if (specificationIds != null) {
                List<Specification> specifications = specificationIds.stream()
                        .map(specificationService::getSpecificationById)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                medicine.setSpecifications(specifications);
            }

            // Lưu Medicine vào database
            medicineService.addMedicine(medicine);
            return ResponseEntity.ok("Medicine added successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    //Add - update medicine
    @PostMapping("/addMedicine")
    public void addMedicine(Medicine medicine) {
        medicineService.addMedicine(medicine);
    }
}
