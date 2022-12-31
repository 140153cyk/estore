package com.store.entity;

import java.util.ArrayList;
import java.util.List;

public class ParentMenuItem extends MenuItem {
    private List<MenuItem> children;
    public ParentMenuItem(int id, String authName) {
        super(id, authName);
        children = new ArrayList<>();
    }

    public void addChild(MenuItem child){
        children.add(child);
    }

    public List<MenuItem> getChildren() {
        return children;
    }

    public void setChildren(List<MenuItem> children) {
        this.children = children;
    }
}
