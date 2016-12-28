package com.github.shynoo.ooad.entity.user;


public class UserBuilder{
    
    User user;
    
    UserBuilder(){
        user = new User();
    }
    
    public UserBuilder id(String o){
        user.setId(o);
        return this;
    }
    
    public UserBuilder name(String name){
        user.setName(name);
        return this;
    }
    
    public UserBuilder password(String password){
        user.setPassword(password);
        return this;
    }
    
    UserBuilder type(UserType userType){
        user.setUserType(userType);
        return this;
    }
    
    
    public User build(){
        return this.user;
    }
    
}
