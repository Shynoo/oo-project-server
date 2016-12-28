package com.github.shynoo.ooad.dao;

import com.github.shynoo.ooad.entity.user.Admin;
import com.github.shynoo.ooad.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        
        User user = User.createNormalUser().id("foo")
            .name("Someone").password("123").build();
        addUser(user);
        
        user = User.createNormalUser().id("11310388")
            .name("邓收港").password("123").build();
        addUser(user);
        
        user = User.createNormalUser().id("11310057")
            .name("张宇").password("123").build();
        addUser(user);
        
        user = Admin.createAdminUser().id("admin")
            .name("Administrator").password("123").build();
        addUser(user);
        
    }
    
    @Override
    public Map<String, User> getAllUsers(){
        Map map=users.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        return map;
    }
    
    @Override
    public int updateUser(String id, User user){
//        users.remove(id);
//        users.put(id,user);
        return 0;
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
