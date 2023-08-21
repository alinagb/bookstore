package com.book.store.bookstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String author;
    private String description;
    private Double price;
    private Integer quantity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileCover coverImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<ChosenItem> chosenItems;

}
