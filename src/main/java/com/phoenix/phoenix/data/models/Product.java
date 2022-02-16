package com.phoenix.phoenix.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Data
public class Product {
    @Id
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
