package vn.edu.iuh.fit.order.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "dob")
    private Instant dob;

    @ColumnDefault("0")
    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "first_name")
    private String firstName;

    @Lob
    @Column(name = "gender")
    private String gender;

    @Column(name = "image")
    private String image;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @ColumnDefault("0")
    @Column(name = "is_verify")
    private Boolean isVerify;

}