package com.book.store.bookstore.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDetailsDto {
    private String fullName;
    private String address;
    private String shippingAddress;
}
