package com.book.store.bookstore.mapper;

import com.book.store.bookstore.dto.UserDto;
import com.book.store.bookstore.entity.Role;
import com.book.store.bookstore.entity.ShoppingCart;
import com.book.store.bookstore.entity.User;
import com.book.store.bookstore.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    public User map(UserDto userDto) {
        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(passwordEncoded);
        user.setAddress(userDto.getAddress());
        Role role = roleRepository.findByName(userDto.getUserRole());
        if(role == null){
            role = saveRole(userDto);
        }
        user.setRoles(List.of(role));

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        return user;
    }


    private Role saveRole(UserDto userDto){
        Role role = new Role();
        role.setName(userDto.getUserRole());
        return roleRepository.save(role);
    }

    public UserDto map(User user) {
        return UserDto.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .confirmPassword(user.getPassword())
                .address(user.getAddress())
                .id(user.getId()).build();
    }
}
