package vn.edu.iuh.fit.inventory.models.dtos.responses;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MedicineResponse {
    private Long id;
    private String des;
    private String desShort;
    private Integer discount;
    private String init;
    private String name;
    private Double price;
    private String primaryImage;
    private String sku;
    private Integer status;
    private Long brandId;
    private Set<Object> categories;
    private Set<Object> images;
    private Set<Object> tags;
}
