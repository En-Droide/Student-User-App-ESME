package com.example.rest;

public class Param {
    private String token,username;
    private int userId;
    private static Param param;

    private Param() {
    }

    public static Param getInstance() {
        if (param == null) {
            param = new Param();
        }
        return param;
    }
    public static Param getIns() {return getInstance();}
    public String getToken() { return token; }

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public void setToken(String token) { this.token = token; }

}
