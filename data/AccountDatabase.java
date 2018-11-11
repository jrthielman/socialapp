package com.example.socialapp.data;

import com.example.socialapp.accounts.Account;
import com.example.socialapp.accounts.User;
import com.example.socialapp.accounts.VeteranUser;

import java.util.ArrayList;
import java.util.List;

public final class AccountDatabase {

    private static AccountDatabase database;

    private List<Account> accountlist = new ArrayList<>();

    private AccountDatabase(){}

    public static synchronized AccountDatabase getDatabase(){
        if(database == null){
            database = new AccountDatabase();
            return database;
        }
        return database;
    }

    public boolean promoteUser(Account user){
        for(int i = 0; i < accountlist.size(); i++){
            if(user.getUsername().equalsIgnoreCase(accountlist.get(i).getUsername())){
                accountlist.set(i,user);
                return true;
            }
        }
        return false;
    }

    public boolean addAccount(String username, String password){
        for(Account acc : accountlist){
            if(username.trim().equalsIgnoreCase(acc.getUsername())){
                return false;
            }
        }
        accountlist.add(new User(username.trim(), password));
        return true;
    }

    public List<Account> getAccountlist() {
        return new ArrayList<>(accountlist);
    }
}
