package vn.edu.iuh.fit.product.services;

import vn.edu.iuh.fit.product.exceptions.MedicineException;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;


public interface ImageService {

    //Get by id
    MedicinesImage getImageById(Long id) throws MedicineException;
}
