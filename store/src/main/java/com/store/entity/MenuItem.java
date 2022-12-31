package com.store.entity;

public class MenuItem {
    private int id;
    private String authName;

    public MenuItem(int id, String authName) {
        this.id = id;
        this.authName = authName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }
}
