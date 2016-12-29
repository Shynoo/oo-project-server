package com.github.shynoo.ooad.entity.user;

import lombok.Data;

@Data
public class Admin extends User{
    
    protected UserType userType = UserType.ADMIN;
    
    public static UserBuilder createAdminUser(){
        return factory.createAdminUser();
    }
    
}
