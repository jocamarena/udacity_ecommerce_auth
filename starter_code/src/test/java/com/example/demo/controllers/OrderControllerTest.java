package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.stubbing.OngoingStubbingImpl;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.persistence.criteria.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    private OrderController orderController = new OrderController();
    private OrderRepository orderRepository = mock(OrderRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    @Before
    public void setUp(){
        TestUtils.inject(orderController, "orderRepository", orderRepository);
        TestUtils.inject(orderController, "userRepository", userRepository);
    }
    @Test
    public void submit(){
        when(userRepository.findByUsername("jcamarena")).thenReturn(createNewUser());
        User user = userRepository.findByUsername("jcamarena");
        List<Item> items = createList(createNewItem());
        UserOrder order = createOrder(user, items);
        when(orderRepository.save(order)).thenReturn(order);
        Assert.assertNotNull(orderRepository.save(order));
    }
    @Test
    public void getOrdersForUser(){
        when(userRepository.findByUsername("jcamarena")).thenReturn(createNewUser());
        User user = userRepository.findByUsername("jcamarena");
        when(orderRepository.findByUser(user)).thenReturn(createUserOrder(user, createList(createNewItem()), 0L));
        List<UserOrder> orderUsers = orderRepository.findByUser(user);
    }
    protected UserOrder createOrder(User user, List<Item> items){
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setId(0L);
        userOrder.setTotal(BigDecimal.valueOf(20.00));
        userOrder.setItems(items);
        return userOrder;
    }
    protected User createNewUser(){
        User user = new User();
        user.setUsername("jcamarena");
        user.setPassword("Clairdel803!");
        user.setId(0L);
        return user;
    }
    protected Item createNewItem(){
        Item item = new Item();
        item.setDescription("desc");
        item.setName("name");
        item.setPrice(BigDecimal.valueOf(2.00));
        item.setId(0L);
        return item;
    }
    protected List<Item> createList(Item item){
        List<Item> items = new ArrayList<>();
        items.add(item);
        return items;
    }
    protected List<UserOrder> createUserOrder(User user, List<Item> items, Long id){
        List<UserOrder> userOrders = new ArrayList<>();
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setTotal(BigDecimal.valueOf(22.00));
        userOrder.setItems(items);
        userOrder.setId(id);
        userOrders.add(userOrder);
        return userOrders;
    }
}
