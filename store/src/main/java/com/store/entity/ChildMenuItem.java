package com.store.entity;

public class ChildMenuItem extends MenuItem{
    private String pageName;
    public ChildMenuItem(int id, String authName,String pageName) {
        super(id, authName);
        this.pageName=pageName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }
}
