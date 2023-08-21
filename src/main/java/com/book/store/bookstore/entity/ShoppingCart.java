package com.book.store.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Integer shoppingCartId;

    @OneToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    private List<ChosenItem> chosenItems;


}
