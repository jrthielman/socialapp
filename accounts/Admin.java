package com.example.socialapp.accounts;

import com.example.socialapp.interfaces.Closable;
import com.example.socialapp.messagesystem.Thread;

public class Admin extends Account {

    private String name;

    public Admin() {
        super("admin", "admin");
    }

    public VeteranUser promoteUser(Account user) {
        return new VeteranUser(user.getUsername(), user.getPassword());
    }

    public void raiseInfleunce(VeteranUser user, int amount){
        user.raiseInfluence(this, amount);
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
