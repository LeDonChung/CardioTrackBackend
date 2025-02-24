package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @Column(name = "address_id", nullable = false)
    private Long id;

    @Lob
    @Column(name = "address_type")
    private String addressType;

    @Column(name = "district")
    private String district;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "province")
    private String province;

    @Column(name = "street")
    private String street;

    @Column(name = "ward")
    private String ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private vn.edu.iuh.fit.order.model.entities.User user;

}