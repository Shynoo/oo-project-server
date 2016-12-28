package com.github.shynoo.ooad.service;

import com.github.shynoo.ooad.entity.book.Book;
import com.github.shynoo.ooad.entity.book.BookStatus;
import com.github.shynoo.ooad.entity.result.Result;
import com.github.shynoo.ooad.entity.user.User;
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
    
    @Autowired
    BookService bookService;
    
    
    @Test
    public void userLogInTest(){
        
        String id = "1";
        String password = "123";
        Result r = userService.checkUserPassword(id, password);
        User u = (User) r.get();
        Assert.assertEquals(u, userService.getUserById(id));
        
    }
    
    @Test
    public void getUserAllBorrowingBooksTest(){
        List<Book> ls=userService.getUserAllBorrowingBooks("1");
        Assert.assertNotNull(ls);
        Assert.assertTrue(ls.size()>0);
    }
    
    @Test
    public void borrowBookTest(){
        userService.borrowBook("1","CS121");
        Book book=bookService.getBookById("CS121");
        List ls=userService.getUserAllBorrowingBooks("1");
        Assert.assertTrue(ls.contains(book));
        Assert.assertTrue(book.getBookStatus().equals(BookStatus.IN_LIBIRARY));
    }
    
}
