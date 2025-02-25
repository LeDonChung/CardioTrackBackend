package vn.edu.iuh.fit.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;

@Repository
public interface ImageRepository extends JpaRepository<MedicinesImage, Long> {
}
