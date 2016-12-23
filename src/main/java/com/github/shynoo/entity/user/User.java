package com.github.shynoo.entity.user;

import com.github.shynoo.entity.book.Book;
import com.github.shynoo.util.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User{
    
    private String id;
    
    private String name;
    
    private transient String password;
    
    public UserType userType;
    
    private List<Book> borrowingBooks;
    
    private final static UserFactory factory=new UserFactory();
    
    public static UserBuilder newBuilder(){
        return new UserBuilder();
    }
    
    public static UserFactory getFactory(){
        return factory;
    }
    
    public boolean couldBorrowNewBook(){
        if (borrowingBooks.size()>=userType.maxBorrowingBookNumber){
            return false;
        }
        for (Book book:borrowingBooks){
            if (DateUtil.defferentNumber(book.getBorrowedDate(),new Date())>=userType.maxBorrowingDay){
                return false;
            }
        }
        return true;
            
    }
    
}
