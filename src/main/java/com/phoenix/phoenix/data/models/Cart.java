package com.phoenix.phoenix.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Item> itemList;

    private double totalPrice;

    public void addItem(Item item){
        if ( itemList == null ){
            itemList = new ArrayList<>();
        }
        itemList.add(item);
    }
}
