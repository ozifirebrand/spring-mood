package com.phoenix.phoenix.data.models;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(length = 500)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @Getter
    private final Cart cart;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @ElementCollection
    private List<Authority> authorities;

    public AppUser(){
        this.cart = new Cart();
    }
}
