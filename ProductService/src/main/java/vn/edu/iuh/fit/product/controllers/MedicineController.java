package vn.edu.iuh.fit.product.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.BaseResponse;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineReponse;
import vn.edu.iuh.fit.product.models.entities.Medicine;
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

    //add - update medicine
    @PostMapping
    public ResponseEntity<BaseResponse<MedicineReponse>> saveMedicine(@RequestBody MedicineRequest medicineRequest) throws MedicineException {
        MedicineReponse medicineReponse = medicineService.save(medicineRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineReponse>builder()
                        .data(medicineReponse)
                        .success(true)
                        .build()
        );
    }

    //update medicine
    @PutMapping
    public ResponseEntity<BaseResponse<MedicineReponse>> updateMedicine(@RequestBody MedicineRequest medicineRequest) throws MedicineException {
        MedicineReponse medicineReponse = medicineService.save(medicineRequest);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineReponse>builder()
                        .data(medicineReponse)
                        .success(true)
                        .build()
        );
    }

    //update status by id
    @PutMapping("/update-status-by-id")
    public ResponseEntity<BaseResponse<MedicineReponse>> updateStatusById(@RequestParam Long id, @RequestParam int status) throws MedicineException {
        MedicineReponse medicineReponse = medicineService.updateStatusById(id, status);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineReponse>builder()
                        .data(medicineReponse)
                        .success(true)
                        .build()
        );
    }

    //Get all medicines
    @GetMapping("/get-all-medicines")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> getAllMedicines() {
        List<MedicineReponse> medicines = medicineService.getAllMedicines();
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by id
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<MedicineReponse>> getMedicineById(@PathVariable Long id) throws MedicineException {
        MedicineReponse medicine = medicineService.getMedicineById(id);
        return ResponseEntity.ok(
                BaseResponse
                        .<MedicineReponse>builder()
                        .data(medicine)
                        .success(true)
                        .build()
        );
    }

    //find by name
    @GetMapping("/get-by-name")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByName(@RequestParam String name) {
        List<MedicineReponse> medicines = medicineService.findMedicineByName(name);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by brand id
    @GetMapping("/get-medicines-by-brand-id/{brandId}")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> getAllMedicinesByBrandId(@PathVariable Long brandId) {
        List<MedicineReponse> medicines = medicineService.getAllMedicinesByBrandId(brandId);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by description
    @GetMapping("/get-by-des")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByDes(@RequestParam String des) {
        List<MedicineReponse> medicines = medicineService.findMedicineByDes(des);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by description short
    @GetMapping("/get-by-des-short")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByDesShort(@RequestParam String desShort) {
        List<MedicineReponse> medicines = medicineService.findMedicineByDesShort(desShort);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by quantity
    @GetMapping("/get-by-quantity")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByQuantity(@RequestParam int quantity) {
        List<MedicineReponse> medicines = medicineService.findMedicineByQuantity(quantity);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by init
    @GetMapping("/get-by-init")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByInit(@RequestParam String init) {
        List<MedicineReponse> medicines = medicineService.findMedicineByInit(init);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by discount
    @GetMapping("/get-medicine-by-discount-between")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByDiscountBetween(@RequestParam int min, @RequestParam int max) {
        List<MedicineReponse> medicines = medicineService.findMedicineByDiscountBetween(min, max);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by price between
    @GetMapping("/get-medicine-by-price-between")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByPriceBetween(@RequestParam double min, @RequestParam double max) {
        List<MedicineReponse> medicines = medicineService.findMedicineByPriceBetween(min, max);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by price less than
    @GetMapping("/get-medicine-by-price-less-than")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByPriceLessThan(@RequestParam double price) {
        List<MedicineReponse> medicines = medicineService.findMedicineByPriceLessThan(price);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }

    //find by price greater than
    @GetMapping("/get-medicine-by-price-greater-than")
    public ResponseEntity<BaseResponse<List<MedicineReponse>>> findMedicineByPriceGreaterThan(@RequestParam double price) {
        List<MedicineReponse> medicines = medicineService.findMedicineByPriceGreaterThan(price);
        return ResponseEntity.ok(
                BaseResponse
                        .<List<MedicineReponse>>builder()
                        .data(medicines)
                        .success(true)
                        .build()
        );
    }
}
