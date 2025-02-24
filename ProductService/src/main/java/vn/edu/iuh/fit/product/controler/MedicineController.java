package vn.edu.iuh.fit.product.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.product.model.entities.Medicine;
import vn.edu.iuh.fit.product.services.MedicineService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    //Get all medicines
    @GetMapping("/getAllMedicines")
    @PreAuthorize("hasAuthority('ADMIN')")
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
}
