package com.phoenix.phoenix.data.models;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Item {
    @Id
//    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantityAdded;

}
