package com.example.campusdash;

public class Store {
    private int storeid;
    private String storename;
    private String vendorname;
    private int vendorid;
    private String storeimage;
    private String rating;

    public Store() {}

    public Store(int storeid, String storename, String vendorname, int vendorid, String storeimage, String rating) {
        this.storeid = storeid;
        this.storename = storename;
        this.vendorname = vendorname;
        this.vendorid = vendorid;
        this.storeimage = storeimage;
        this.rating = rating;
    }

    public int getStoreid() { return storeid; }
    public void setStoreid(int storeid) { this.storeid = storeid; }

    public String getStorename() { return storename; }
    public void setStorename(String storename) { this.storename = storename; }

    public String getVendorname() { return vendorname; }
    public void setVendorname(String vendorname) { this.vendorname = vendorname; }

    public int getVendorid() { return vendorid; }
    public void setVendorid(int vendorid) { this.vendorid = vendorid; }

    public String getStoreimage() { return storeimage; }
    public void setStoreimage(String storeimage) { this.storeimage = storeimage; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
}
