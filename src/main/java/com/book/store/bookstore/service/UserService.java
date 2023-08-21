package com.book.store.bookstore.service;

import com.book.store.bookstore.dto.UserDetailsDto;
import com.book.store.bookstore.dto.UserDto;
import com.book.store.bookstore.dto.UserUpdateDto;
import com.book.store.bookstore.entity.User;
import com.book.store.bookstore.mapper.UserMapper;
import com.book.store.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;


    public void saveUser(UserDto userDto) {
        User user = userMapper.map(userDto);
        userRepository.save(user);
    }

    public void updateUser(UserUpdateDto userDto, String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setAddress(userDto.getAddress());
            user.get().setFullName(userDto.getFullName());
            String passwordEncoded = passwordEncoder.encode(userDto.getPassword());
            user.get().setPassword(passwordEncoded);
            userRepository.save(user.get());
        }
    }


    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> userMapper.map(user))
                .collect(Collectors.toList());
    }

    public UserDetailsDto getUserDetailsDtoByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setAddress(user.getAddress());
        userDetailsDto.setFullName(user.getFullName());
        return userDetailsDto;
    }

    public UserUpdateDto getUser(String loggedInUserEmail) {
        Optional<User> user = userRepository.findByEmail(loggedInUserEmail);
        return UserUpdateDto.builder()
                .address(user.get().getAddress())
                .fullName(user.get().getFullName()).build();

    }
}
