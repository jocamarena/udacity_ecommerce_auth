package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    private CartController cartController = mock(CartController.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    @Before
    public void setUp(){
        cartController = new CartController();
        TestUtils.inject(cartController, "cartRepository", cartRepository);
    }
    @Test
    public void addTocart(){
        Cart cart = new Cart();
        when(cartRepository.save(cart)).thenReturn(cart);
        cart.addItem(createNewItem());
        Assert.assertNotNull(cartRepository.save(cart));
    }
    @Test
    public void removeFromcart(){
        Cart cart = new Cart();
        when(cartRepository.save(cart)).thenReturn(cart);
        cart.addItem(createNewItem());
        Assert.assertNotNull(cartRepository.save(cart));
        cart.removeItem(createNewItem());
        Assert.assertTrue(cart.getItems().isEmpty());
    }
    protected ModifyCartRequest addToCart(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(createNewItem().getId());
        modifyCartRequest.setUsername("jcamarena");
        modifyCartRequest.setQuantity(1);
        return modifyCartRequest;
    }
    protected Item createNewItem(){
        Item item = new Item();
        item.setId(0L);
        item.setPrice(BigDecimal.valueOf(2.00));
        item.setName("name");
        item.setDescription("desc");
        return item;
    }
}
