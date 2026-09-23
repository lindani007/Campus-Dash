package com.example.campusdash;

public class Meal {
    private int mealid;
    private String mealname;
    private String meaimage;
    private double mealprice;
    private int storeid;
    private String mealcatergory;

    public Meal() {}

    public Meal(int mealid, String mealname, String meaimage, double mealprice, int storeid, String mealcatergory) {
        this.mealid = mealid;
        this.mealname = mealname;
        this.meaimage = meaimage;
        this.mealprice = mealprice;
        this.storeid = storeid;
        this.mealcatergory = mealcatergory;
    }

    public int getMealid() { return mealid; }
    public void setMealid(int mealid) { this.mealid = mealid; }

    public String getMealname() { return mealname; }
    public void setMealname(String mealname) { this.mealname = mealname; }

    public String getMeaimage() { return meaimage; }
    public void setMeaimage(String meaimage) { this.meaimage = meaimage; }

    public double getMealprice() { return mealprice; }
    public void setMealprice(double mealprice) { this.mealprice = mealprice; }

    public int getStoreid() { return storeid; }
    public void setStoreid(int storeid) { this.storeid = storeid; }

    public String getMealcatergory() { return mealcatergory; }
    public void setMealcatergory(String mealcatergory) { this.mealcatergory = mealcatergory; }
}
