package com.example.campusdash;

public class Vendor {
    private int vendorid;
    private String vendorName;
    private String storeName;
    private String vendorEmail;

    public Vendor() {}

    public Vendor(int vendorid, String vendorName, String storeName, String vendorEmail) {
        this.vendorid = vendorid;
        this.vendorName = vendorName;
        this.storeName = storeName;
        this.vendorEmail = vendorEmail;
    }

    public int getVendorid() { return vendorid; }
    public void setVendorid(int vendorid) { this.vendorid = vendorid; }

    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }

    public String getVendorEmail() { return vendorEmail; }
    public void setVendorEmail(String vendorEmail) { this.vendorEmail = vendorEmail; }
}
