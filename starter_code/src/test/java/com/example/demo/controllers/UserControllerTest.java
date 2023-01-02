package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.Slf4JLoggingSystem;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);
    private UserController userController = mock(UserController.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setUp(){
        userController = new UserController();
        TestUtils.inject(userController, "userRepository", userRepository);
        TestUtils.inject(userController, "cartRepository", cartRepository);
        TestUtils.inject(userController, "bCryptPasswordEncoder", encoder);
    }
    @Test
    public void happyTest(){
        when(encoder.encode("test")).thenReturn("thisIsHashed");
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setPassword("pw");
        userRequest.setConfirmPassword("pw");
        userRequest.setUsername("jcamarena");
        final ResponseEntity<User> responseEntity = userController.createUser(userRequest);
        log.debug("Username set to : "+ userRequest.getUsername());
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @Test
    public void createUser(){
        CreateUserRequest createUserRequest = createUserRequest();
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        log.debug("responseEntity " + responseEntity.getBody().getUsername() +
                " id:" + responseEntity.getBody().getId());
        //mock(userRepository.save(any(Class<User.class>))).thenReturn();
        Assert.assertEquals(createUserRequest.getUsername(), responseEntity.getBody().getUsername());
    }
    @Test
    public void findById(){
        when(userRepository.findById(0L)).thenReturn(Optional.of(createNewUser()));
        CreateUserRequest createUserRequest = createUserRequest();
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        Optional<User> optionalUser = userRepository.findById(responseEntity.getBody().getId());
        Assert.assertTrue(optionalUser.isPresent());
        Assert.assertEquals(optionalUser.get().getId(), responseEntity.getBody().getId());
    }
    @Test
    public void findByUserName(){
        when(userRepository.findById(0L)).thenReturn(Optional.of(createNewUser()));
        when(userRepository.findByUsername("jcamarena")).thenReturn(createNewUser());
        CreateUserRequest createUserRequest = createUserRequest();
        ResponseEntity<User> responseEntity = userController.createUser(createUserRequest);
        User user  = userRepository.findByUsername("jcamarena");
        Assert.assertEquals(user.getId(), responseEntity.getBody().getId());
        log.debug("Assertion findByUsername " + (user.getId() == responseEntity.getBody().getId()));
    }
    protected CreateUserRequest createUserRequest(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("jcamarena");
        createUserRequest.setPassword("Clairdel803!");
        createUserRequest.setConfirmPassword("Clairdel803!");
        return  createUserRequest;
    }
    protected String generateSalt(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    protected User createNewUser(){
        User user = new User();
        user.setUsername("jcamarena");
        user.setPassword("Clairdel803!");
        user.setId(0L);
        return user;
    }
}
