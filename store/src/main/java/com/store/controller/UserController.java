package com.store.controller;

import com.store.entity.User;
import com.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService service;
    public UserController(@Autowired UserService service){
        this.service = service;
    }

    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public boolean checkLogin(@RequestBody Map<String,String> paras){
        String name = paras.getOrDefault("name",null);
        String pass = paras.getOrDefault("password",null);
        if(name==null||pass==null)return false;
        return service.login(name,pass);
    }

    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public int register(@RequestBody User user){
       return service.register(user);
    }

}
