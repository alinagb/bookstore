package com.book.store.bookstore.service;

import com.book.store.bookstore.entity.ChosenItem;
import com.book.store.bookstore.entity.CustomerOrder;
import com.book.store.bookstore.entity.ShoppingCart;
import com.book.store.bookstore.entity.User;
import com.book.store.bookstore.repository.ChosenItemRepository;
import com.book.store.bookstore.repository.CustomerOrderRepository;
import com.book.store.bookstore.repository.ShoppingCartRepository;
import com.book.store.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerOrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ChosenItemRepository chosenItemRepository;

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    public void addCustomerOrder(String loggedInUserEmail, String shippingAddress) {
        Optional<User> optionalUser = userRepository.findByEmail(loggedInUserEmail);
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(loggedInUserEmail);

        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(optionalUser.get());
        customerOrder.setShippingAddress(shippingAddress);
        customerOrderRepository.save(customerOrder);

        for (ChosenItem chosenProduct : shoppingCart.getChosenItems()) {
            chosenProduct.setShoppingCart(null);
            chosenProduct.setCustomerOrder(customerOrder);
            chosenItemRepository.save(chosenProduct);
        }
    }
}
