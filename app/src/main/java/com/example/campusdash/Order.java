package com.example.campusdash;

public class Order {
    private int id;
    private String orderNumber;
    private String studentName;
    private String vendorName;
    private double amount;
    private String status;

    public Order() {}

    public Order(int id, String orderNumber, String studentName, String vendorName, double amount, String status) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.studentName = studentName;
        this.vendorName = vendorName;
        this.amount = amount;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
