package com.example.socialapp;

import com.example.socialapp.accounts.Account;
import com.example.socialapp.accounts.Admin;
import com.example.socialapp.accounts.User;
import com.example.socialapp.data.AccountDatabase;
import com.example.socialapp.exceptions.MustLoginException;
import com.example.socialapp.exceptions.NotAuthorizedException;
import com.example.socialapp.messagesystem.Conversation;
import com.example.socialapp.messagesystem.Message;
import com.example.socialapp.messagesystem.Thread;

public class MockApplication {

    public static void main(String[] args) {
        AccountDatabase.getDatabase().addAccount("javiel", "Thielman");
        MainWindow main = new MainWindow("DragonTalkZ");
        main.assignAdmin();
        try{
            main.addThread(new Thread("Main questions"));
        }catch(NotAuthorizedException ne){
            System.out.println("You are not authorized");
        }catch(MustLoginException ee){
            System.out.println("You are not logged in");
        }
        main.start();
    }

}
