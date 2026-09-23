package com.example.campusdash;

public class DeliveryGuy {
    private int id;
    private String deliveryguyname;
    private String email;

    public DeliveryGuy() {}

    public DeliveryGuy(int id, String deliveryguyname, String email) {
        this.id = id;
        this.deliveryguyname = deliveryguyname;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDeliveryguyname() { return deliveryguyname; }
    public void setDeliveryguyname(String deliveryguyname) { this.deliveryguyname = deliveryguyname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
