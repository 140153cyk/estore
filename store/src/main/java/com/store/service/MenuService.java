package com.store.service;

import com.store.entity.ChildMenuItem;
import com.store.entity.MenuItem;
import com.store.entity.ParentMenuItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    public List<MenuItem> getMenus(){
        List<MenuItem> menus = new ArrayList<>();
        ParentMenuItem store = new ParentMenuItem(1,"商城");
        ParentMenuItem myShop = new ParentMenuItem(2,"我的店铺");
        ParentMenuItem mine = new ParentMenuItem(3,"我的");

        store.addChild(new ChildMenuItem(4,"商品","ProductPage"));
        store.addChild(new ChildMenuItem(5,"历史订单","UserOrderPage"));
        store.addChild(new ChildMenuItem(6,"购物车","ShoppingCartPage"));

        myShop.addChild(new ChildMenuItem(7,"管理店铺","MyStorePage"));
        myShop.addChild(new ChildMenuItem(8,"查看订单","StoreOrderPage"));

        mine.addChild(new ChildMenuItem(9,"我的信息","MinePage"));

        menus.add(store);
        menus.add(myShop);
        menus.add(mine);

        return menus;
    }
}
