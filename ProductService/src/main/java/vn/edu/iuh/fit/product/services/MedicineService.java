package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineReponse;
import vn.edu.iuh.fit.product.models.entities.Medicine;

import java.util.List;


public interface MedicineService {
    //Add - update medicine
    MedicineReponse save(MedicineRequest medicineRequest) throws MedicineException;

    //get all medicines
    List<MedicineReponse> getAllMedicines();

    //update status by id
    MedicineReponse updateStatusById(Long id, int status) throws MedicineException;

    //find by id
    MedicineReponse getMedicineById(Long id) throws MedicineException;

    //find by name
    List<MedicineReponse> findMedicineByName(String name);

    //find by brand id
    List<MedicineReponse> getAllMedicinesByBrandId(Long brandId);


    //find by description
    List<MedicineReponse> findMedicineByDes(String des);

    //find by short description
     List<MedicineReponse> findMedicineByDesShort(String desShort);

    //find by discount
    List<MedicineReponse> findMedicineByDiscountBetween(int min, int max);

    //find by init
    List<MedicineReponse> findMedicineByInit(String init);

    //find by price between
    List<MedicineReponse> findMedicineByPriceBetween(double min, double max);

    //find by price less than
    List<MedicineReponse> findMedicineByPriceLessThan(double price);

    //find by price greater than
    List<MedicineReponse> findMedicineByPriceGreaterThan(double price);

    //find by quantity
    List<MedicineReponse> findMedicineByQuantity(int quantity);
}
