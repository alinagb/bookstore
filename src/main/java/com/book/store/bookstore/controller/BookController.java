package com.book.store.bookstore.controller;

import com.book.store.bookstore.dto.ChosenItemDto;
import com.book.store.bookstore.dto.ItemDto;
import com.book.store.bookstore.dto.ShoppingCartDto;
import com.book.store.bookstore.dto.UserDetailsDto;
import com.book.store.bookstore.entity.Item;
import com.book.store.bookstore.service.ItemService;
import com.book.store.bookstore.service.CustomerOrderService;
import com.book.store.bookstore.service.ShoppingCartService;
import com.book.store.bookstore.service.UserService;
import com.book.store.bookstore.validator.ItemValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemValidator itemValidator;

    @GetMapping("/items")
    public String listItems(Model model) {
        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        ChosenItemDto chosenItemDto = new ChosenItemDto();
        model.addAttribute("chosenItemDto", chosenItemDto);
        return "items";
    }

    @GetMapping("/")
    public String welcome(Model model) {
        boolean authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        if(authenticated){
            List<Item> items = itemService.findAllItems();
            model.addAttribute("items", items);
            ChosenItemDto chosenItemDto = new ChosenItemDto();
            model.addAttribute("chosenItemDto", chosenItemDto);
            return "items";
        }else{
            return "login";
        }
    }

    @GetMapping("item")
    public String viewBook(Model model) {
        ItemDto itemDto = ItemDto.builder().build();
        model.addAttribute("item", itemDto);
        return "item";
    }


    @PostMapping("/item")
    public String saveBook(@ModelAttribute("item") ItemDto itemDto, BindingResult bindingResult, Model model,
                           @RequestParam("coverImage") MultipartFile multipartFile) throws IOException {

        itemValidator.validate(itemDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", itemDto);
            return "item";
        }

        itemService.saveBook(itemDto, multipartFile);

        List<Item> items = itemService.findAllItems();
        model.addAttribute("items", items);
        ChosenItemDto chosenItemDto = new ChosenItemDto();
        model.addAttribute("chosenItemDto", chosenItemDto);

        return "redirect:/items";
    }


    @PostMapping("/item/{itemId}")
    public String viewProductPost(@PathVariable(value = "itemId") String itemId,
                                  @ModelAttribute ChosenItemDto chosenItemDto,
                                  BindingResult result,
                                  Model model) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("chosenItemDto", chosenItemDto);
        shoppingCartService.addToCart(chosenItemDto, itemId, loggedInUserEmail);
        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String cartGet(Model model) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

       // afisam toate produsele care au fost adaugate de catre user
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartDtoByUserEmail(loggedInUserEmail);
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        return "cart";
    }

    @GetMapping("/checkout")
    public String checkoutGet(Model model) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartDtoByUserEmail(loggedInUserEmail);
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        UserDetailsDto userDetailsDto = userService.getUserDetailsDtoByEmail(loggedInUserEmail);
        model.addAttribute("userDetailsDto", userDetailsDto);

        return "checkout";
    }

    @PostMapping("/sendOrder")
    public String sendOrder(@ModelAttribute("userDetailsDto") UserDetailsDto userDetailsDto) {
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        customerOrderService.addCustomerOrder(loggedInUserEmail, userDetailsDto.getShippingAddress());
        return "confirmation";
    }


}
