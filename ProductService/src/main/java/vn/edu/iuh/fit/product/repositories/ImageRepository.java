package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.product.model.entities.MedicinesImage;

public interface ImageRepository extends JpaRepository<MedicinesImage, Long> {
}
