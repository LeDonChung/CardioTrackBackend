package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @Column(name = "medicine_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "des")
    private String des;

    @Lob
    @Column(name = "des_short")
    private String desShort;

    @Column(name = "discount", nullable = false)
    private Integer discount;

    @Column(name = "init")
    private String init;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "primary_image")
    private String primaryImage;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "reviews", nullable = false)
    private Integer reviews;

    @Column(name = "sku")
    private String sku;

    @Column(name = "slug")
    private String slug;

    @Column(name = "specifications")
    private String specifications;

    @Column(name = "star", nullable = false)
    private Integer star;

    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}