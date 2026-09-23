package com.example.campusdash;

public class CartItem {
    private int cartId;
    private int mealId;
    private String mealName;
    private double mealPrice;
    private String mealImage;
    private int quantity;

    public CartItem(int cartId, int mealId, String mealName, double mealPrice, String mealImage, int quantity) {
        this.cartId = cartId;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealPrice = mealPrice;
        this.mealImage = mealImage;
        this.quantity = quantity;
    }

    public int getCartId() { return cartId; }
    public int getMealId() { return mealId; }
    public String getMealName() { return mealName; }
    public double getMealPrice() { return mealPrice; }
    public String getMealImage() { return mealImage; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}