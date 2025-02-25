package vn.edu.iuh.fit.product.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;
import vn.edu.iuh.fit.product.repositories.ImageRepository;
import vn.edu.iuh.fit.product.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    //Get by id
    @Override
    public MedicinesImage getImageById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }
}
