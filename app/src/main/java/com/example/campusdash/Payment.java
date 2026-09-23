package com.example.campusdash;

public class Payment {
    private int paymentid;
    private String paymentmethod;
    private String orderid;
    private double amount;

    public Payment() {}

    public Payment(int paymentid, String paymentmethod, String orderid, double amount) {
        this.paymentid = paymentid;
        this.paymentmethod = paymentmethod;
        this.orderid = orderid;
        this.amount = amount;
    }

    public int getPaymentid() { return paymentid; }
    public void setPaymentid(int paymentid) { this.paymentid = paymentid; }

    public String getPaymentmethod() { return paymentmethod; }
    public void setPaymentmethod(String paymentmethod) { this.paymentmethod = paymentmethod; }

    public String getOrderid() { return orderid; }
    public void setOrderid(String orderid) { this.orderid = orderid; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
