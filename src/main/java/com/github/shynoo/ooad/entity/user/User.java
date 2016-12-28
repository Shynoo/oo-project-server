package com.github.shynoo.ooad.entity.user;

import lombok.Data;

@Data
public class User{
    
    protected String id;
    
    protected String name;
    
    protected transient String password;
    
    protected UserType userType;
    
    protected final static UserFactory factory = new UserFactory();
    
    public static UserFactory getFactory(){
        return factory;
    }
    
    public static UserBuilder createNormalUser(){
        return factory.createNormalUser();
    }
    
    public static UserBuilder createAdvanceUser(){
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
