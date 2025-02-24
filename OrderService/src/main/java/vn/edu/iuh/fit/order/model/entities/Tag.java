package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @Column(name = "tag_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "des")
    private String des;

    @Column(name = "title")
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_group_id")
    private vn.edu.iuh.fit.order.model.entities.TagsGroup tagGroup;

}