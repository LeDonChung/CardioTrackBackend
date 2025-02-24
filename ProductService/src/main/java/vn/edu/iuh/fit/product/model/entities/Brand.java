package vn.edu.iuh.fit.product.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "brand")
    private Set<Medicine> medicines = new LinkedHashSet<>();

}