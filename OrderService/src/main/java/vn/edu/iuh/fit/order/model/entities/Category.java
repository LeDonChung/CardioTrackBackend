package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "full_path_slug")
    private String fullPathSlug;

    @Column(name = "icon")
    private String icon;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

}