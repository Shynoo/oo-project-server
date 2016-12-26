package com.github.shynoo.controller;

import com.github.shynoo.entity.result.Result;
import com.github.shynoo.service.BookService;
import com.github.shynoo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SystemController{
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;
    
    
    SystemController(){
        
    }
    
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Result logIn(@RequestParam String id, @RequestParam String password
//        ,HttpServletResponse response
    ){
        
        return userService.userLogIn(id, password);
    }
    
    @RequestMapping(value = "/test")
    public String test(){
        return "test ok";
    }
    
    
}
