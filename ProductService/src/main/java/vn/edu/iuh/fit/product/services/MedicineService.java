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

    //update status by id
    public void updateStatusById(Long id, int status) {
        Medicine medicine = medicineRepositories.findById(id).orElse(null);
        if (medicine != null) {
            medicine.setStatus(status);
            medicineRepositories.save(medicine);
        }
    }

    //find by id
    public Medicine getMedicineById(Long id) {
        return medicineRepositories.findById(id).orElse(null);
    }

    //find by brand id
    public List<Medicine> getAllMedicinesByBrandId(Long brandId) {
        return medicineRepositories.findAllByBrandId(brandId);
    }

    //find by name
    public List<Medicine> findMedicineByName(String name) {
        return medicineRepositories.findMedicineByName(name);
    }

    //find by description
    public List<Medicine> findMedicineByDes(String des) {
        return medicineRepositories.findMedicineByDes(des);
    }

    //find by short description
    public List<Medicine> findMedicineByDesShort(String desShort) {
        return medicineRepositories.findMedicineByDesShort(desShort);
    }

    //find by discount
    public List<Medicine> findMedicineByDiscountBetween(int min, int max) {
        return medicineRepositories.findMedicineByDiscountBetween(min, max);
    }

    //find by init
    public List<Medicine> findMedicineByInit(String init) {
        return medicineRepositories.findMedicineByInit(init);
    }
}
