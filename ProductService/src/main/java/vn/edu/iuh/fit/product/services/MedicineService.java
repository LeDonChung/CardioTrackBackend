package vn.edu.iuh.fit.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.model.entities.Medicine;
import vn.edu.iuh.fit.product.repositories.MedicineRepositories;

import java.util.List;

@Service
public class MedicineService {
    @Autowired
    private MedicineRepositories medicineRepositories;

    //get all medicines
    public List<Medicine> getAllMedicines() {
        return medicineRepositories.findAll();
    }
    //Add - update medicine
    public void addMedicine(Medicine medicine) {
        medicineRepositories.save(medicine);
    }
}
