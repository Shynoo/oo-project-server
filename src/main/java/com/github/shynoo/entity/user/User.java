package com.github.shynoo.entity.user;

import lombok.Data;

@Data
public class User{
    
    private String id;
    
    private String name;
    
    private transient String password;
    
    public UserType userType;
    
    private final static UserFactory factory = new UserFactory();
    
    public static UserBuilder newBuilder(){
        return new UserBuilder();
    }
    
    public static UserFactory getFactory(){
        return factory;
    }
    
    public boolean couldManageUser(){
        if (userType.isAllowManageUsers()) {
            return true;
        }
        return false;
    }
    
}
