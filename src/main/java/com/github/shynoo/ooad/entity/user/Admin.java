package com.github.shynoo.ooad.entity.user;

public class Admin extends User{
    
    public final UserType userType = UserType.ADMIN;
    
    public static UserBuilder createAdminUserBuilder(){
        return factory.createAdminUser();
    }
    
    
}
