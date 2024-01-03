package com.example.project12;

import java.io.Serializable;

public class Admin implements Serializable {
    private String userName;
    private String password;

    Admin(){
        userName = "";
        password = "";
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
