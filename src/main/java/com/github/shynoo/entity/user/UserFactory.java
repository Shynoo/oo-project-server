package com.github.shynoo.entity.user;

public class UserFactory{
    
    public UserBuilder createNormalUser(){
        return new UserBuilder().type(UserType.NORMAL);
    }
    
    public UserBuilder createAdvanceUser(){
        return new UserBuilder().type(UserType.ADVANCE);
    }
    
//    UserBuilder createAdministor(){
//        return new UserBuilder().type(UserType.ADMIN);
//    }
    
}
