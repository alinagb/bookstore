package com.book.store.bookstore.repository;

import com.book.store.bookstore.entity.ChosenItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChosenItemRepository extends JpaRepository<ChosenItem, Integer> {
}
