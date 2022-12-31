package com.store.dao;

import com.store.entity.Order;
import com.store.entity.OrderItem;
import com.store.entity.OrderStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderMapper {
    public List<Order> getOrdersByStoreId(@Param("id") String id);
    public List<OrderItem> getOrderItemsByOrder(@Param("id") String id);
    public List<Order> getOrdersByUser(@Param("user")String customerName);
    public List<Order> getCartByUser(@Param("user") String customerName);

    public void addCart(@Param("order")Order order);
    public void addOrder(@Param("order") Order order);
    public void addOrderItem(@Param("item")OrderItem item,@Param("parent")String parentId);

    public void setOrderStatus(@Param("id")String id, @Param("status")OrderStatus status);
    public void setOrderAddress(@Param("id")String id,@Param("address")String address);
    public void setOrderTime(@Param("id")String id, @Param("time")Date time);

    public void removeOrder(@Param("id")String id);
    public void removeOrderByProduct(@Param("id")String id);
}
