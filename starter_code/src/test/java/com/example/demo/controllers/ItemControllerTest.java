package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.hibernate.internal.util.collections.ConcurrentReferenceHashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    private ItemController itemController = mock(ItemController.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    @Before
    public void setUp(){
        itemController = new ItemController();
        TestUtils.inject(itemController, "itemRepository", itemRepository);
    }
    @Test
    public void getItems(){
        List<Item> items = new ArrayList<>();
        items.add(createNewItem());
        when(itemRepository.findAll()).thenReturn(items);
        Assert.assertTrue(!itemController.getItems().getBody().isEmpty());
    }
    @Test
    public void getItemById(){
        Item item = createNewItem();
        when(itemRepository.findById(0L)).thenReturn(Optional.of(item));
        ResponseEntity<Item> responseEntity = itemController.getItemById(item.getId());
        Assert.assertNotNull(responseEntity.getBody());
    }
    @Test
    public void getItemsByName(){
        List<Item> items = new ArrayList<>();
        Item item = createNewItem();
        items.add(item);
        when(itemRepository.findByName("name")).thenReturn(items);
        ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName(item.getName());
        Assert.assertNotNull(!responseEntity.getBody().isEmpty());
    }
    protected Item createNewItem(){
        Item item = new Item();
        item.setDescription("desc");
        item.setName("name");
        item.setPrice(BigDecimal.valueOf(2.00));
        item.setId(0L);
        return item;
    }
}
