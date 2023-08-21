package com.book.store.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserUpdateDto {
    private Integer id;
    private String fullName;
    private String password;
    private String confirmPassword;
    private String address;
}
