package com.example.campusdash;

public class Order {
    private String orderid;
    private int mealid;
    private int quantity;
    private String date; // Standard string format for SQLite ISO dates
    private String location;
    private String userEmail;
    private String orderstatus;

    public Order() {}

    public Order(String orderid, int mealid, int quantity, String date, String location, String userEmail, String orderstatus) {
        this.orderid = orderid;
        this.mealid = mealid;
        this.quantity = quantity;
        this.date = date;
        this.location = location;
        this.userEmail = userEmail;
        this.orderstatus = orderstatus;
    }

    public String getOrderid() { return orderid; }
    public void setOrderid(String orderid) { this.orderid = orderid; }

    public int getMealid() { return mealid; }
    public void setMealid(int mealid) { this.mealid = mealid; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getOrderstatus() { return orderstatus; }
    public void setOrderstatus(String orderstatus) { this.orderstatus = orderstatus; }
}
