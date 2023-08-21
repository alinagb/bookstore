package com.book.store.bookstore.repository;

import com.book.store.bookstore.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {


}
