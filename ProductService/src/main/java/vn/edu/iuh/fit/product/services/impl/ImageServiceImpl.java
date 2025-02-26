package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.exceptions.MedicineImageException;
import vn.edu.iuh.fit.product.mappers.MedicineImageMapper;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineImageResponse;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;
import vn.edu.iuh.fit.product.repositories.ImageRepository;
import vn.edu.iuh.fit.product.services.ImageService;
import vn.edu.iuh.fit.product.utils.SystemConstraints;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MedicineImageMapper medicineImageMapper;

    //Get by id
    @Override
    public MedicineImageResponse getImageById(Long id) throws MedicineImageException {
        Optional<MedicinesImage> medicinesImage = imageRepository.findById(id);
        if(medicinesImage.isEmpty()) {
            throw new MedicineImageException(SystemConstraints.MEDICINE_IMAGE_NOT_FOUND);
        }
        return medicineImageMapper.toResponse(medicinesImage.get());
    }
}
