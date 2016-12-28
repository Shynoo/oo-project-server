package com.github.shynoo.entity.user;

public class UserFactory{
    
    UserBuilder createNormalUser(){
        return new UserBuilder().type(UserType.NORMAL);
    }
    
    UserBuilder createAdvanceUser(){
        return new UserBuilder().type(UserType.ADVANCE);
    }
    
}
