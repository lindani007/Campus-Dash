package com.example.campusdash;
public class User {
    private String userEmail;
    private String userHashedPassword;
    private String fullnames;
    private String role;
    private String phonenumber;

    public User() {}

    public User(String userEmail, String userHashedPassword, String fullnames, String role, String phonenumber) {
        this.userEmail = userEmail;
        this.userHashedPassword = userHashedPassword;
        this.fullnames = fullnames;
        this.role = role;
        this.phonenumber = phonenumber;
    }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserHashedPassword() { return userHashedPassword; }
    public void setUserHashedPassword(String userHashedPassword) { this.userHashedPassword = userHashedPassword; }

    public String getFullnames() { return fullnames; }
    public void setFullnames(String fullnames) { this.fullnames = fullnames; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }
}
