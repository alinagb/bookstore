package com.book.store.bookstore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private String confirmPassword;
    private String address;
    private String userRole;

}
