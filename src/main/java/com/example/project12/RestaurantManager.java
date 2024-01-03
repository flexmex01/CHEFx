package com.example.project12;

import java.io.Serializable;

public class RestaurantManager implements Serializable {
    private String userName;
    private String password;

    public RestaurantManager() {
        this.userName = "";
        this.password = "";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
