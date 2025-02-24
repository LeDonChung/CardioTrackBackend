package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "points_histories")
public class PointsHistory {
    @Id
    @Column(name = "point_history_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "change_type")
    private String changeType;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "points_balance", nullable = false)
    private Integer pointsBalance;

    @Column(name = "points_change", nullable = false)
    private Integer pointsChange;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "point_id")
    private Point point;

}