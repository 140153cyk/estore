package com.store.entity;

public class Address {
    private String id;
    private AddressPoint point;
    private String district;
    private String detail;

    public Address() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AddressPoint getPoint() {
        return point;
    }

    public void setPoint(AddressPoint point) {
        this.point = point;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
