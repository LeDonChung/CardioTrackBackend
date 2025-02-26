package vn.edu.iuh.fit.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.user.enums.AddressType;

@Entity
@Table(name = "addresss_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddressDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private String fullName;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "order_id", columnDefinition = "BIGINT DEFAULT NULL")
    private Long orderId;
}
