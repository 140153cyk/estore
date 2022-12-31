package com.store.service;

import com.store.dao.AddressMapper;
import com.store.entity.Address;
import com.store.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private AddressMapper mapper;
    public AddressService(@Autowired AddressMapper mapper){
        this.mapper = mapper;
    }
    public void addAddress(String user,Address address){
        address.setId(IdentityUtil.generateSequenceNo());
        mapper.addAddress(user,address);
    }
    public List<Address> getAddressesByUser(String user){
        return mapper.getAddressedByUser(user);
    }
    public void deleteAddress(String id ){
        mapper.deleteById(id);
    }
}
