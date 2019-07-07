package com.example.demo.entity;

public class wxOrder {
    private int orderId;
    private int userId;
    private int productId;
    private int addressId;
    private int statusCode;

    private String userName;
    private String phoneNumber;
    private String region;
    private String detail;
    private String productName;
    private String createOrderTime;
    private String picture;
    private String price;

    public wxOrder(){};

    public wxOrder(int userId, int productId, int addressId, int statusCode) {
        this.userId = userId;
        this.productId = productId;
        this.addressId = addressId;
        this.statusCode = statusCode;
    }

    public wxOrder(int statusCode, String userName, String phoneNumber, String region, String detail, String productName, String createOrderTime, String picture, String price) {
        this.statusCode = statusCode;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.detail = detail;
        this.productName = productName;
        this.createOrderTime = createOrderTime;
        this.picture = picture;
        this.price = price;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCreateOrderTime() {
        return createOrderTime;
    }

    public void setCreateOrderTime(String createOrderTime) {
        this.createOrderTime = createOrderTime;
    }

    public String getPicture() {
        return "http://localhost:8080/image"+picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "wxOrder{" +
                "statusCode=" + statusCode +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", region='" + region + '\'' +
                ", detail='" + detail + '\'' +
                ", productName='" + productName + '\'' +
                ", createOrderTime='" + createOrderTime + '\'' +
                ", picture='" + picture + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
