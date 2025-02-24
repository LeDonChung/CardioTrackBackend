package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags_groups")
public class TagsGroup {
    @Id
    @Column(name = "tag_group_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "des")
    private String des;

    @Column(name = "group_name")
    private String groupName;

}