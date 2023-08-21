package com.book.store.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChosenItem {

    @Id
    @GeneratedValue
    private Integer chosenItemId;

    private Integer chosenQuantity;

    @ManyToOne
    @JoinColumn
    private Item item;

    @ManyToOne
    @JoinColumn
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn
    private CustomerOrder customerOrder;

}
