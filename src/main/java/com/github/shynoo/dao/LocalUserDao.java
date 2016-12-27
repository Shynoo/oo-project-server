package com.github.shynoo.dao;

import com.github.shynoo.entity.user.Admin;
import com.github.shynoo.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("userDao")
public class LocalUserDao implements UserDao{
    
    private Map<String, User> users;
    
    private Map<String, String> idToPassword;
    
    public LocalUserDao(){
        
        users = new ConcurrentHashMap();
        idToPassword = new ConcurrentHashMap<>();
        
        initData();
    }
    
    @Override
    public void initData(){
        
        User user = User.getFactory().createNormalUser().id("1")
            .name("张三").password("123").build();
        addUser(user);
        
        user = User.getFactory().createAdvanceUser().id("2")
            .name("高级用户李四").password("123").build();
        addUser(user);
        
        user = User.getFactory().createAdvanceUser().id("11310388")
            .name("邓收港").password("123").build();
        addUser(user);
        
        user = User.getFactory().createAdvanceUser().id("11310057")
            .name("张宇").password("123").build();
        addUser(user);
        
        user = Admin.newAdmin().id("admin1")
            .name("管理员01").password("123").build();
        addUser(user);
        
    }
    
    @Override
    public Map<String, User> getAllUsers(){
        Map<String,User> map=new HashMap<>();
        for (String s:users.keySet()){
            map.put(s,users.get(s));
        }
        return map;
    }
    
    
    @Override
    public String getUserPassword(String id){
        return idToPassword.get(id);
    }
    
    @Override
    public User getUserById(String id){
        return users.get(id);
    }
    
    
    @Override
    public int addUser(User user){
        idToPassword.put(user.getId(), user.getPassword());
        users.put(user.getId(), user);
        user.setPassword("");
        return 0;
    }
    
    @Override
    public int deleteUser(User user){
        users.remove(user.getId());
        idToPassword.remove(user.getId());
        return 0;
    }
    
    
}
