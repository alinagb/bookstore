package com.book.store.bookstore.repository;

import com.book.store.bookstore.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findByUserEmail(String email);
}
