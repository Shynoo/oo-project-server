package com.github.shynoo.ooad.entity.user;

public class UserFactory{
    
    UserBuilder createNormalUser(){
        return new UserBuilder().type(UserType.NORMAL);
    }
    
    UserBuilder createAdvanceUser(){
        return new UserBuilder().type(UserType.ADVANCE);
    }
    
    UserBuilder createAdminUser(){
        return new UserBuilder().type(UserType.ADMIN);
    }
}
