package com.github.shynoo.entity.user;


import com.github.shynoo.entity.book.Book;

import java.util.List;

public class UserBuilder{
    
    User user=new User();
    
    UserBuilder(){
        
    }
    
    public UserBuilder id(String o){
        user.setId(o);
        return this;
    }
    
    public UserBuilder name(String name){
        user.setName(name);
        return this;
    }
    
    public UserBuilder password(String password){
        user.setPassword(password);
        return this;
    }
    
    UserBuilder type(UserType userType){
        user.setUserType(userType);
        return this;
    }
    
    public UserBuilder borrowindBooks(List<Book> books){
        user.setBorrowingBooks(books);
        return this;
    }
    
    public User build(){
        return this.user;
    }
    
}
