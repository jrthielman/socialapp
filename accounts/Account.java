package com.example.socialapp.accounts;

import com.example.socialapp.exceptions.LoginFailedException;

public abstract class Account {

    private String username;
    private String password;
    private boolean online;

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean login(String password) throws LoginFailedException{

        if(online){
            System.out.println("You are already logged in");
            return true;
        } else if(password.equalsIgnoreCase(this.password)){
            online = true;
            return true;
        }
        throw new LoginFailedException();
    }

    public void logout(){
        if(online){
            online = false;
        }else{
            System.out.println("You are already logged out");
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return online;
    }
}
