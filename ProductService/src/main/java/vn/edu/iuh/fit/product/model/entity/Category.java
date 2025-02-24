package vn.edu.iuh.fit.product.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long id;

    @Column(name = "full_path_slug", length = 255, nullable = false)
    private String fullPathSlug;

    @Column(name = "icon", length = 500)
    private String icon;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    // Quan hệ ManyToOne: Danh mục con -> Danh mục cha
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "category_id")
    private Category parent;

    // Quan hệ OneToMany: Danh mục cha -> Danh mục con
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> children;

    // Setter ánh xạ `parent_id` vào `parent`
    @JsonSetter("parent_id")
    public void setParentById(Long parentId) {
        if (parentId != null) {
            this.parent = new Category();
            this.parent.setId(parentId);
        }
    }

//    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
//    private List<Medicine> medicines;

}
