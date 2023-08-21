package com.book.store.bookstore.enums;

public enum UserRole {

    ROLE_USER,
    ROLE_ADMIN;
    public String friendlyName(){
        return switch (this) {
            case ROLE_USER -> "Customer";
            case ROLE_ADMIN -> "Admin";
        };
    }
}
