package com.phoenix.phoenix.data.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(fetch = FetchType.EAGER)
    private Product product;

    private int quantityAdded;

    public Item(Product product, int quantityAdded){
        if ( quantityAdded <= product.getQuantity() )
        this.quantityAdded = quantityAdded;
        else {
            this.quantityAdded = 0;
        }
        this.product = product;
    }

}
