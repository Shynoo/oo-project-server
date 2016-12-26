package com.github.shynoo.service;

import com.github.shynoo.dao.BookDao;
import com.github.shynoo.dao.UserDao;
import com.github.shynoo.entity.book.Book;
import com.github.shynoo.entity.book.BookStatus;
import com.github.shynoo.entity.result.Result;
import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService{
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private BookDao bookDao;
    
    UserService(){
        
    }
    
    public void initData(){
        userDao.initData();
        bookDao.initData();
        initBorrowData();
    }
    
    public Result userLogIn(String id, String password){
        
        String realPasswd = userDao.getUserPassword(id);
        
        if (password.equals(realPasswd)) {
            
            User user = userDao.getUserById(id);
            
            return new Result(ResultStatus.SUCCESS, user);
        }
        
        return new Result(ResultStatus.FAILURE);
        
    }
    
    public Result getUserById(String id){
        User user = userDao.getUserById(id);
        return new Result(ResultStatus.SUCCESS, user);
    }
    
    
    public ResultStatus addUser(User user, User newUser){
        
        if (user.getUserType().isAllowManageUsers()) {
            ResultStatus rs = userDao.addUser(newUser);
            return rs;
        }
        return (ResultStatus.FAILURE);
        
    }
    
    
    public ResultStatus deletUser(User user, User delUser){
        try{
            if (user.getUserType().isAllowManageUsers()) {
                return (userDao.deleteUser(delUser));
            }
        } catch(Exception e){
            
            return (ResultStatus.UNKNOWN_RESULT);
            
        }
        return (ResultStatus.FAILURE);
    }
    
    
    public ResultStatus addBook(User user, Book book){
        if (user.getUserType().isAllowAddBooks()) {
            bookDao.addBook(book);
            return ResultStatus.SUCCESS;
        }
        return ResultStatus.FAILURE;
    }
    
    
    public ResultStatus deleteBook(User user, Book book){
        if (user.getUserType().isAllowAddBooks()) {
            return bookDao.deleteBook(book);
        }
        return ResultStatus.UNKNOWN_RESULT;
    }
     
    boolean isInited=false;
    
    public ResultStatus borrowBook(User user, Book book){
        if (!isInited){
            initData();
        }
        
        
        if (!user.couldBorrowNewBook()) {
            return ResultStatus.FAILURE;
        }
        if (!book.getBookStatus().equals(BookStatus.IN_LIBIRARY)) {
            return ResultStatus.FAILURE;
        }
        
        user.borrowBook(book);
        book.borrowOut(user);
        
        return ResultStatus.SUCCESS;
    }
    
    
    public ResultStatus giveBack(Book book){
        book.getReader().giveBackBook(book);
        book.giveBack();
        return ResultStatus.SUCCESS;
    }
    
    public void initBorrowData(){
        this.borrowBook((User) this.getUserById("1").get(),this.getRandomBook());
        this.borrowBook((User) this.getUserById("1").get(),this.getRandomBook());
        this.borrowBook((User) this.getUserById("1").get(),this.getRandomBook());
        this.borrowBook((User) this.getUserById("11310057").get(),this.getRandomBook());
        this.borrowBook((User) this.getUserById("11310057").get(),this.getRandomBook());
        this.borrowBook((User) this.getUserById("11310057").get(),this.getRandomBook());
        
    }
    
    public boolean checkUserCouldBorrowBook(User user){
        return user.couldBorrowNewBook();
    }
    
    public Book getRandomBook(){
        return bookDao.getRandomBook();
    }
    
}
