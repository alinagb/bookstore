package com.book.store.bookstore.validator;

import com.book.store.bookstore.dto.ItemDto;
import com.book.store.bookstore.dto.UserDto;
import com.book.store.bookstore.entity.User;
import com.book.store.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;

@Service
public class ItemValidator {
    @Autowired
    private UserRepository userRepository;

    public void validate(ItemDto itemDto, BindingResult bindingResult) {

        if (itemDto.getTitle().isEmpty()) {
            FieldError fieldError = new FieldError("itemDto", "title", "Please fill mandatory fields");
            bindingResult.addError(fieldError);
        }
        if (Double.parseDouble(itemDto.getQuantity()) <= 0) {
            FieldError fieldError = new FieldError("itemDto", "quantity", "Quantity should be greater then 0");
            bindingResult.addError(fieldError);
        }
    }



}
