package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.exceptions.MedicineImageException;
import vn.edu.iuh.fit.product.models.dtos.responses.MedicineImageResponse;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;


public interface ImageService {

    //Get by id
    MedicineImageResponse getImageById(Long id) throws MedicineImageException;
}
