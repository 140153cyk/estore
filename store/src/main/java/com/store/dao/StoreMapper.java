package com.store.dao;

import com.store.entity.Store;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreMapper {
    //返回所有商店，不包含其中的具体商品
    public List<Store> getFullStoreByOwner(@Param("owner") String owner);
    public Store getFullStore(@Param("id") String id);

    public void addStore(@Param("id") String id,@Param("name") String name,@Param("owner")String owner);
}
