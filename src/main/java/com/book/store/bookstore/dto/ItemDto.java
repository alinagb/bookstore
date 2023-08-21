package com.book.store.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ItemDto {
    private String title;
    private String author;
    private String price;
    private String description;
    private String image;
    private String quantity;
}
