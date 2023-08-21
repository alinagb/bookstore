package com.book.store.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class CustomerOrder {

    @Id
    @GeneratedValue
    private Integer customerOrderId;
    private String shippingAddress;
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "customerOrder")
    private List<ChosenItem> chosenItems;

}
