package com.store.service;

import com.store.dao.UserMapper;
import com.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class UserService {
    private UserMapper userMapper;
    public UserService(@Autowired UserMapper mapper){
        this.userMapper = mapper;
    }

    public boolean login(String name, String password){
        String encryption = encrypt(password);
        return userMapper.checkPwd(name,encryption);
    }

    public String encrypt(String source){
        return DigestUtils.md5DigestAsHex(source.getBytes());
    }

    public int register(User user){
        if(userMapper.existName(user.getName()))return 1001;
        try{
            String encryption = encrypt(user.getPassword());
            userMapper.register(user.getName(), encryption, user.getEmail());
            return 1000;
        }catch (Exception e){
            return 1002;
        }

    }

}
