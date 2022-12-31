package com.store.controller;

import com.store.entity.MenuItem;
import com.store.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private MenuService service;
    public MenuController(@Autowired MenuService service){
        this.service=service;
    }

    @RequestMapping(path = "/getMenus",method = RequestMethod.GET)
    public List<MenuItem> getMenus(){
        return service.getMenus();
    }
}
