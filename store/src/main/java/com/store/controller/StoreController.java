package com.store.controller;

import com.store.entity.Store;
import com.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    private StoreService service;
    public StoreController(@Autowired StoreService service){
        this.service = service;
    }

    @RequestMapping(path = "/owner",method = RequestMethod.GET)
    public List<Store> getStoresByOwner(String name){
        return service.getStoresByOwner(name);
    }

    @RequestMapping(path = "/id/{id}",method= RequestMethod.GET)
    public Store getStoreById(@PathVariable String id){return service.getStoreById(id);}

    @RequestMapping(path = "/addStore",method = RequestMethod.POST)
    public int addStore(String name,String owner){return service.addStore(name,owner);}
}
