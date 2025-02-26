package vn.edu.iuh.fit.product.models.dtos.requests;

import lombok.*;
import vn.edu.iuh.fit.product.models.entities.MedicinesImage;

import java.util.LinkedHashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineRequest {
    private Long id;

    private String des;

    private String desShort;

    private Integer discount;

    private String init;

    private String name;

    private Double price;

    private String primaryImage;

    private Integer quantity;

    private Integer reviews;

    private String sku;

    private String slug;

    private String specifications;

    private Integer star;

    private Integer status;

    private Long brandId;

    private Long categoryId;

    private Set<MedicinesImage> medicinesImages = new LinkedHashSet<>();

}
