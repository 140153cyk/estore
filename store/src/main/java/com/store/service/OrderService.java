package com.store.service;

import com.store.dao.OrderMapper;
import com.store.dao.ProductMapper;
import com.store.entity.Order;
import com.store.entity.OrderItem;
import com.store.entity.OrderStatus;
import com.store.entity.Product;
import com.store.util.IdentityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private OrderMapper mapper;
    private ProductMapper products;

    public OrderService(@Autowired OrderMapper mapper,@Autowired ProductMapper products){
        this.mapper = mapper;
        this.products = products;
    }

    public void addCart(Order order){
        order.setId(IdentityUtil.generateSequenceNo());
        order.setTime(new Date());
        mapper.addOrder(order);
        for (OrderItem item : order.getItems()) {
            item.setId(IdentityUtil.generateSequenceNo());
            mapper.addOrderItem(item,order.getId());
        }
    }


    public int purchase(Order order){
        Product product = order.getItems().get(0).getProduct();
        int quantity = products.getQuantityById(product.getId());
        if(quantity<order.getItems().get(0).getCount())return -1;
        product.setQuantity(quantity-order.getItems().get(0).getCount());
        products.updateProduct(product);

        order.setStatus(OrderStatus.Paid);
        order.setId(IdentityUtil.generateSequenceNo());
        order.setTime(new Date());
        mapper.addOrder(order);
        for (OrderItem item : order.getItems()) {
            item.setId(IdentityUtil.generateSequenceNo());
            mapper.addOrderItem(item,order.getId());
        }
        return 0;
    }

    public int purchaseForCart(Order order){
        Product product = order.getItems().get(0).getProduct();
        int quantity = products.getQuantityById(product.getId());
        if(quantity<order.getItems().get(0).getCount())return -1;
        product.setQuantity(quantity-order.getItems().get(0).getCount());
        products.updateProduct(product);
        setOrderStatus(order.getId(),OrderStatus.Paid);
        mapper.setOrderAddress(order.getId(), order.getAddress().getId());
        mapper.setOrderTime(order.getId(), new Date());
        return 0;
    }

    public void finishOrder(String id){
        mapper.setOrderStatus(id,OrderStatus.Finished);
    }

    public List<Order> getOrdersByUser(String name){
        return mapper.getOrdersByUser(name);
    }

    public List<Order> getCartByUser(String name){
        return mapper.getCartByUser(name);
    }

    public void setOrderStatus(String id, OrderStatus status){
        mapper.setOrderStatus(id,status);
    }

    public void removeOrder(String id){
        mapper.removeOrder(id);
    }

}
