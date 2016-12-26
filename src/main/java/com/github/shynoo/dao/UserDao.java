package com.github.shynoo.dao;


import com.github.shynoo.entity.user.User;

public interface UserDao{
    
    String getUserPassword(String id);
    
    User getUserById(String id);
    
    int addUser(User user);
    
    int deleteUser(User user);
    
    void initData();
}
