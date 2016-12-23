package com.github.shynoo.dao;


import com.github.shynoo.entity.result.ResultStatus;
import com.github.shynoo.entity.user.User;

public interface UserDao{
    
    String getUserPassword(String id);
    
    User getUserById(String id);
    
    ResultStatus addUser(User user);
    
    ResultStatus deleteUser(User user);
    
}
