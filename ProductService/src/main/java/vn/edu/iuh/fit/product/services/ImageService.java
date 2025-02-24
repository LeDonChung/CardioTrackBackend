package vn.edu.iuh.fit.product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.model.entities.MedicinesImage;
import vn.edu.iuh.fit.product.repositories.ImageRepository;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    //Get by id
    public MedicinesImage getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
