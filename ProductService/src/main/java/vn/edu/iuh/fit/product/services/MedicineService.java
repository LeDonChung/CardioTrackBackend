package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.models.dtos.PageDTO;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineSearchRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineResponse;

import java.util.List;


public interface MedicineService {
    //Add - update medicine
    MedicineResponse save(MedicineRequest medicineRequest) throws MedicineException;

    //get all medicines
    List<MedicineResponse> getAllMedicines();

    //update status by id
    MedicineResponse updateStatusById(Long id, int status) throws MedicineException;

    //find by id
    MedicineResponse getMedicineById(Long id) throws MedicineException;


    PageDTO<MedicineResponse> getPages(int page, int size, String sortBy, String sortName);

    boolean isExists(Long id);

    PageDTO<MedicineResponse> search(MedicineSearchRequest request, int page, int size, String sortBy, String sortName);

    List<MedicineResponse> getAll();
}
