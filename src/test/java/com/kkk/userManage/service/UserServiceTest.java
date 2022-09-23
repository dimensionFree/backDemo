package com.kkk.userManage.service;


import com.kkk.userManage.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    UserService userService;
    User user;

    @Before
    public void setUp() throws Exception {
        user = User.builder()
                .username("test")
                .password("test").build();
    }

    @Test
    public void loadUserByUsername() {

    }

    @Test
    public void saveUser() {
        userService.saveUser(user);
    }
}
