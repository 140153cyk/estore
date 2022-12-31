package com.store.controller;

import com.store.entity.Order;
import com.store.entity.OrderStatus;
import com.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService service;
    public OrderController(@Autowired OrderService service){
        this.service = service;
    }
    @RequestMapping(path = "/addCart",method = RequestMethod.POST)
    public void addToCart(@RequestBody Order order){
        order.setStatus(OrderStatus.Created);
        service.addCart(order);
    }
    @RequestMapping(path = "/payOrder",method = RequestMethod.POST)
    public int addPaidOrder(@RequestBody Order order){
        return service.purchase(order);
    }

    @RequestMapping(path = "/payForCart",method = RequestMethod.PUT)
    public int payForCart(@RequestBody Order order) {
        return service.purchaseForCart(order);
    }

    @RequestMapping(path = "/finishOrder/{id}",method = RequestMethod.PUT)
    public void finishOrder(@PathVariable String id){
        service.finishOrder(id);
    }

    @RequestMapping(path = "/customer",method = RequestMethod.GET)
    public List<Order> getOrdersByUser(String username){
        return service.getOrdersByUser(username);
    }

    @RequestMapping(path = "/cart",method = RequestMethod.GET)
    public List<Order> getCartByUser(String username){
        return service.getCartByUser(username);
    }

    @RequestMapping(path = "/cancel",method = RequestMethod.PUT)
    public void cancelOrder(String id){
        service.setOrderStatus(id,OrderStatus.Cancelled);
    }

    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    public void deleteOrder(@PathVariable String id){
        service.removeOrder(id);
    }


}
