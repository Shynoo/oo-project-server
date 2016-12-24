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
    
    public Result userLogIn(String id, String password){
        
        String realPasswd=userDao.getUserPassword(id);
        
        if (password.equals(realPasswd)){
            
            User user=userDao.getUserById(id);
            
            return new Result(ResultStatus.SUCCESS,user);
        }
        
        return new Result(ResultStatus.FAILURE);
        
    }
    
    
    public Result addUser(User user,User newUser){
        
        if (user.getUserType().isAllowManageUsers()){
            ResultStatus rs=userDao.addUser(newUser);
            return new Result(rs);
        }
        return new Result(ResultStatus.FAILURE);
        
    }
    
    
    public Result deletUser(User user,User delUser){
        try{
            if (user.getUserType().isAllowManageUsers()){
                return new Result(userDao.deleteUser(delUser));
            }
        } catch (Exception e){
            
            return new Result(ResultStatus.UNKNOWN_RESULT);
            
        }
        return new Result(ResultStatus.FAILURE);
    }
    
    
    public ResultStatus addBook(User user, Book book){
        if (user.getUserType().isAllowAddBooks()){
            bookDao.addBook(book);
            return ResultStatus.SUCCESS;
        }
        return ResultStatus.FAILURE;
    }
    
    
    public ResultStatus deleteBook(User user,Book book){
        if (user.getUserType().isAllowAddBooks()){
            return bookDao.delBook(book);
        }
        return ResultStatus.UNKNOWN_RESULT;
    }
    
    public ResultStatus borrowBook(User user,Book book){
        
        if (!user.couldBorrowNewBook()){
            return ResultStatus.FAILURE;
        }
        if (!book.getBookStatus().equals(BookStatus.IN_LIBIRARY)){
            return ResultStatus.FAILURE;
        }
        
        user.borrowBook(book);
        book.borrowOut(user);
        
        return ResultStatus.SUCCESS;
    }
    
    
    public ResultStatus dischargeBook(Book book){
        book.getReader().giveBackBook(book);
        book.giveBack();
        return ResultStatus.SUCCESS;
    }
    
    public boolean checkUserCouldBorrowBook(User user){
        return user.couldBorrowNewBook();
    }
    
    
}
