package com.store.dao;

import com.store.entity.Address;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressMapper {
    public Address getAddressById(@Param("id") String id);
    public List<Address> getAddressedByUser(@Param("user") String user);
    public void addAddress(@Param("user")String user,@Param("address")Address address);
    public void deleteById(@Param("id")String id);

}
