package com.github.shynoo.entity.user;

import lombok.Data;

@Data
public class User{
    
    private String id;
    
    private String name;
    
    private transient String password;
    
    public UserType userType;
    
    private final static UserFactory factory = new UserFactory();
    
    
    public static UserFactory getFactory(){
        return factory;
    }
    
    public static UserBuilder createNormalUserBuilder(){
        return factory.createNormalUser();
    }
    
    public static UserBuilder createAdvanceUserBuilder(){
        return factory.createAdvanceUser();
    }
    
    
    public boolean couldManageBook(){
        return userType.isAllowManageBooks();
    }
    
    public boolean couldManageUser(){
        if (userType.isAllowManageUsers()) {
            return true;
        }
        return false;
    }
    
}
