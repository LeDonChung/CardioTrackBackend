package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.BrandException;
import vn.edu.iuh.fit.product.exceptions.CategoryException;
import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.mappers.MedicineMapper;
import vn.edu.iuh.fit.product.models.dtos.requests.MedicineRequest;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineReponse;
import vn.edu.iuh.fit.product.models.entities.Brand;
import vn.edu.iuh.fit.product.models.entities.Category;
import vn.edu.iuh.fit.product.models.entities.Medicine;
import vn.edu.iuh.fit.product.repositories.BrandRepository;
import vn.edu.iuh.fit.product.repositories.CategoryRepository;
import vn.edu.iuh.fit.product.repositories.MedicineRepository;
import vn.edu.iuh.fit.product.services.MedicineService;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private MedicineMapper medicineMapper;

    //Add - update medicine
    @Override
    public MedicineReponse save(MedicineRequest medicineRequest) throws MedicineException {
        Medicine medicine = null;
        //Insert
        if(medicineRequest.getId() == null) {
            medicine = medicineMapper.toEntity(medicineRequest);
            if(medicineRequest.getCategoryId() == null) {
                throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
            }else{
                Optional<Category> category = categoryRepository.findById(medicineRequest.getCategoryId());
                if(category.isEmpty()) {
                    throw new CategoryException(SystemConstraints.CATEGORY_NOT_FOUND);
                }
                else {
                    medicine.setCategory(category.get());
                }
            }

            if(medicineRequest.getBrandId() == null) {
                throw new BrandException(SystemConstraints.BRAND_NOT_FOUND);
            }else{
                Optional<Brand> brand = brandRepository.findById(medicineRequest.getBrandId());
                if(brand.isEmpty()) {
                    throw new BrandException(SystemConstraints.BRAND_NOT_FOUND);
                }
                else {
                    medicine.setBrand(brand.get());
                }
            }
        }
        // Update
        else{
            Optional<Medicine> oldMedicine = medicineRepository.findById(medicineRequest.getId());
            if (oldMedicine.isEmpty()) {
                throw new MedicineException(SystemConstraints.MEDICINE_NOT_FOUND);
            }else{
                medicine = medicineMapper.partialUpdate(medicineRequest, oldMedicine.get());

                //Update category and brand
                Optional<Category> category = categoryRepository.findById(medicineRequest.getCategoryId());
                medicine.setCategory(category.get());

                Optional<Brand> brand = brandRepository.findById(medicineRequest.getBrandId());
                medicine.setBrand(brand.get());
            }
        }

        medicine = medicineRepository.save(medicine);
        return medicineMapper.toResponse(medicine);
    }

    //update status by id
    @Override
    public MedicineReponse updateStatusById(Long id, int status) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if (medicine.isEmpty()) {
            return null;
        }
        medicine.get().setStatus(status);
        medicineRepository.save(medicine.get());
        return medicineMapper.toResponse(medicine.get());
    }

    //get all medicines
    @Override
    public List<MedicineReponse> getAllMedicines() {
        List<Medicine> medicines = medicineRepository.findAll();
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by id
    @Override
    public MedicineReponse getMedicineById(Long id) throws MedicineException {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        if (medicine.isEmpty()) {
            throw new MedicineException(SystemConstraints.MEDICINE_NOT_FOUND);
        }
        return medicineMapper.toResponse(medicine.get());
    }

    //find by name
    @Override
    public List<MedicineReponse> findMedicineByName(String name) {
        List<Medicine> medicines = medicineRepository.findMedicineByName(name);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by brand id
    @Override
    public List<MedicineReponse> getAllMedicinesByBrandId(Long brandId) {
        List<Medicine> medicines = medicineRepository.findAllByBrandId(brandId);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by description
    @Override
    public List<MedicineReponse> findMedicineByDes(String des) {
        List<Medicine> medicines = medicineRepository.findMedicineByDes(des);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by short description
    @Override
    public List<MedicineReponse> findMedicineByDesShort(String desShort) {
        List<Medicine> medicines = medicineRepository.findMedicineByDesShort(desShort);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by discount
    @Override
    public List<MedicineReponse> findMedicineByDiscountBetween(int min, int max) {
        List<Medicine> medicines = medicineRepository.findMedicineByDiscountBetween(min, max);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by quantity
    @Override
    public List<MedicineReponse> findMedicineByQuantity(int quantity) {
        List<Medicine> medicines = medicineRepository.findMedicineByQuantity(quantity);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by init
    @Override
    public List<MedicineReponse> findMedicineByInit(String init) {
        List<Medicine> medicines = medicineRepository.findMedicineByInit(init);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by price between
    @Override
    public List<MedicineReponse> findMedicineByPriceBetween(double min, double max) {
        List<Medicine> medicines = medicineRepository.findMedicineByPriceBetween(min, max);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by price less than
    @Override
    public List<MedicineReponse> findMedicineByPriceLessThan(double price) {
        List<Medicine> medicines = medicineRepository.findMedicineByPriceLessThan(price);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }

    //find by price greater than
    @Override
    public List<MedicineReponse> findMedicineByPriceGreaterThan(double price) {
        List<Medicine> medicines = medicineRepository.findMedicineByPriceGreaterThan(price);
        return medicines.stream().map(medicine -> medicineMapper.toResponse(medicine)).toList();
    }
}
