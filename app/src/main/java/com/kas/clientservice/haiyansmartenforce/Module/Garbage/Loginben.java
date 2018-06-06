package com.kas.clientservice.haiyansmartenforce.Module.Garbage;

/**
 * Created by 12976 on 2018/4/28.
 */

public class Loginben {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Loginben(String username, String password){
        super();
        this.username=username;
        this.password=password;

    }
}
