package com.book.store.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FileCover {

    @Id
    @GeneratedValue
    private Integer id;

    private String path;

    @OneToOne(mappedBy = "coverImage")
    private Item item;

}
