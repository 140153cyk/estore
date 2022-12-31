package com.store.dao;

import com.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    public boolean checkPwd(@Param("name") String name, @Param("password") String encryption);

    public void register(@Param("name") String name, @Param("password") String encryption, @Param("email") String email);

    public boolean existName(@Param("name") String name);

}
