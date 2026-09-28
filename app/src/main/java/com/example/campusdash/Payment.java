package com.example.campusdash;

public class Payment {
    private int id;
    private String paymentNumber;
    private String studentName;
    private String vendorName;
    private double amount;
    private String method;
    private String status;

    public Payment() {}

    public Payment(int id, String paymentNumber, String studentName, String vendorName, double amount, String method, String status) {
        this.id = id;
        this.paymentNumber = paymentNumber;
        this.studentName = studentName;
        this.vendorName = vendorName;
        this.amount = amount;
        this.method = method;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPaymentNumber() { return paymentNumber; }
    public void setPaymentNumber(String paymentNumber) { this.paymentNumber = paymentNumber; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
