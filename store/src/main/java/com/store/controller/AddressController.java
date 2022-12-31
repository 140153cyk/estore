package com.store.controller;

import com.store.entity.Address;
import com.store.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
public class AddressController {
    private AddressService service;
    public AddressController(@Autowired AddressService service){
        this.service = service;
    }
    @RequestMapping(path = "/getByUser",method = RequestMethod.GET)
    public List<Address> getAddressesByUser(String username){
        return service.getAddressesByUser(username);
    }
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public void addAddress(String username, @RequestBody Address address){
        service.addAddress(username,address);
    }
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable String id){
        service.deleteAddress(id);
    }
}
