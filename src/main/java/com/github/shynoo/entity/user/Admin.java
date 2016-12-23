package com.github.shynoo.entity.user;

public class Admin extends User{
    
    public final UserType userType=UserType.ADMIN;
    
    public UserBuilder newAdminUser(){
        return new UserBuilder().type(UserType.ADMIN);
    }
    
    
}
