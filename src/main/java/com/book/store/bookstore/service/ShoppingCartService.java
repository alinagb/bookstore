package com.book.store.bookstore.service;

import com.book.store.bookstore.dto.ChosenItemDto;
import com.book.store.bookstore.dto.ShoppingCartDto;
import com.book.store.bookstore.dto.ShoppingCartItemDto;
import com.book.store.bookstore.entity.Item;
import com.book.store.bookstore.entity.ChosenItem;
import com.book.store.bookstore.entity.ShoppingCart;
import com.book.store.bookstore.entity.User;
import com.book.store.bookstore.repository.ItemRepository;
import com.book.store.bookstore.repository.ChosenItemRepository;
import com.book.store.bookstore.repository.ShoppingCartRepository;
import com.book.store.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChosenItemRepository chosenItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public void addToCart(ChosenItemDto chosenItemDto, String productId, String loggedInUserEmail) {
        ChosenItem chosenProduct = buildChosenProduct(chosenItemDto, productId, loggedInUserEmail);
        chosenItemRepository.save(chosenProduct);

    }

    private ChosenItem buildChosenProduct(ChosenItemDto chosenItemDto, String productId, String loggedInUserEmail) {
        ChosenItem chosenProduct = new ChosenItem();
        chosenProduct.setChosenQuantity(Integer.valueOf(chosenItemDto.getQuantity()));

        Optional<Item> optionalProduct = itemRepository.findById(Integer.valueOf(productId));
        chosenProduct.setItem(optionalProduct.get());

        Optional<User> optionalUser = userRepository.findByEmail(loggedInUserEmail);
        chosenProduct.setShoppingCart(optionalUser.get().getShoppingCart());

        return chosenProduct;
    }

    public ShoppingCartDto getShoppingCartDtoByUserEmail(String loggedInUserEmail) {

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(loggedInUserEmail);

        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        double subTotal = 0;

        for(ChosenItem chosenProduct : shoppingCart.getChosenItems()){

            ShoppingCartItemDto shoppingCartItemDto = new ShoppingCartItemDto();

            shoppingCartItemDto.setName(chosenProduct.getItem().getTitle());
            shoppingCartItemDto.setQuantity(String.valueOf(chosenProduct.getChosenQuantity()));
            shoppingCartItemDto.setPrice(String.valueOf(chosenProduct.getItem().getPrice()));

            double auxiliaryPrice = chosenProduct.getChosenQuantity() * chosenProduct.getItem().getPrice();
            shoppingCartItemDto.setTotal(String.valueOf(auxiliaryPrice));

            subTotal = subTotal + auxiliaryPrice;

            shoppingCartDto.add(shoppingCartItemDto);

        }
        shoppingCartDto.setSubTotal(String.valueOf(subTotal));
        shoppingCartDto.setTotal(String.valueOf(subTotal + 50));

        return shoppingCartDto;
    }
}
