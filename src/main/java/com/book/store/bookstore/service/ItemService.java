package com.book.store.bookstore.service;

import com.book.store.bookstore.dto.ItemDto;
import com.book.store.bookstore.entity.Item;
import com.book.store.bookstore.mapper.BookMapper;
import com.book.store.bookstore.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookMapper bookMapper;

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public void saveBook(ItemDto itemDto, MultipartFile multipartFile) throws IOException {
        Item item = bookMapper.map(itemDto, multipartFile);
        itemRepository.save(item);
    }
}
