package com.github.shynoo.service;


import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.result.Result;
import com.github.shynoo.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest{
    
    @Autowired
    UserService userService;
    
    @Test
    public void userLogInTest(){
        String id = "1";
        String password = "123";
        Result r = userService.checkPassword(id, password);
        User u = (User) r.get();
        Assert.assertEquals(u, userService.getUserById(id));
    }
    
    @Test
    public void getUserAllBorrowingBooksTest(){
        List<Book> ls=userService.getUserAllBorrowingBooks("1");
        Assert.assertNotNull(ls);
        Assert.assertTrue(ls.size()>0);
    }
    
}
