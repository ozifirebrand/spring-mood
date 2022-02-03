package com.phoenix.phoenix.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Data
public class Product {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String imageUrl;

    @CreationTimestamp
    private LocalDate dateCreated;

}
