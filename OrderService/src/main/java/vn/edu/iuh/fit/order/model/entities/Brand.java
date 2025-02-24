package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @Column(name = "brand_id", nullable = false)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "title")
    private String title;

}