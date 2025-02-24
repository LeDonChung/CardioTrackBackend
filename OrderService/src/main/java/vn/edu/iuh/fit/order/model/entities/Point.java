package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "points")
public class Point {
    @Id
    @Column(name = "point_id", nullable = false)
    private Long id;

    @Column(name = "current_point", nullable = false)
    private Integer currentPoint;

    @Column(name = "rank")
    private Byte rank;

    @Column(name = "update_at")
    private Instant updateAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private vn.edu.iuh.fit.order.model.entities.User user;

}