package com.github.shynoo.ooad.dao;


import com.github.shynoo.ooad.entity.user.User;

import java.util.Map;

public interface UserDao{
    
    String getUserPassword(String id);
    
    User getUserById(String id);
    
    int addUser(User user);
    
    int deleteUser(User user);
    
    void initData();
    
    Map<String,User> getAllUsers();
    
    int updateUser(String id,User user);
}
