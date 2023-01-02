package com.example.demo;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sun.net.www.http.HttpClient;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SareetaApplication.class)
public class SareetaApplicationTests {
	@Test
	public void contextLoads() {
	}
}
