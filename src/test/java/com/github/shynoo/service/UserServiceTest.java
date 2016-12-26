package com.github.shynoo.service;


import com.github.shynoo.entity.result.Result;
import com.github.shynoo.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest{
    @Autowired
    UserService userService;
    
    @Test
    public void userLogInTest(){
        String id = "1";
        String password = "11310057";
        Result r = userService.userLogIn(id, password);
        User u = (User) r.get();
        Assert.assertEquals(u, (User) userService.getUserById(id).get());
        
    }
    
    
}
