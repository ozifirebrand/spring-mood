package com.phoenix.phoenix.data.models;

import javax.persistence.*;
import java.util.Date;

@Entity
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
    private Date dateCreated;
    private String imageUrl;

}
