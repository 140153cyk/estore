package com.store.service;

import com.store.dao.StoreMapper;
import com.store.entity.Store;
import com.store.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class StoreService {
    private StoreMapper mapper;
    public StoreService(@Autowired StoreMapper mapper){
        this.mapper = mapper;
    }

    public List<Store> getStoresByOwner(String owner){
        return  mapper.getFullStoreByOwner(owner);
    }
    public Store getStoreById(String id){return mapper.getFullStore(id);}

    public int addStore(String name,String owner){
        try{
            String id = IdentityUtil.generateSequenceNo();
            mapper.addStore(id,name,owner);
            return 0;
        }catch (Exception e){
            return -1;
        }
    }
}
