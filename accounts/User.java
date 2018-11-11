package com.example.socialapp.accounts;

import com.example.socialapp.interfaces.Bannable;
import com.example.socialapp.messagesystem.Conversation;
import com.example.socialapp.messagesystem.Message;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User extends Account implements Bannable {

    private String firstName, lastName;
    private int userRating;
    private boolean banned;
    private Date dateJoined;
    private final int MAX_WARNING = 10;
    private int amountOfWarnings;

    private List<String> messageHistory = new ArrayList<>();

    public User(String username, String password){
        super(username,password);
        this.dateJoined = Date.valueOf(LocalDate.now());
    }

    public Conversation createConversation(String title){
        return new Conversation(title);
    }

    public Message postMessage(String message){
        messageHistory.add(message);
        return new Message(this, message);
    }

    void raiseRating(){
        this.userRating++;
    }

    boolean report(){
        if(amountOfWarnings < MAX_WARNING){
            this.amountOfWarnings++;
            return true;
        }
        return false;
    }

    @Override
    public boolean banUser() {
        if(!banned){
            banned = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean unbanUser() {
        if(banned){
            banned = false;
            return true;
        }
        return false;
    }

    public void addPersonalInformation(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public List<String> getMessageHistory() {
        return new ArrayList<>(messageHistory);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getUserRating() {
        return userRating;
    }

    public int getAmountOfPosts() {
        return messageHistory.size();
    }

    public boolean isBanned() {
        return banned;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public int getMAX_WARNING() {
        return MAX_WARNING;
    }

    public int getAmountOfWarnings() {
        return amountOfWarnings;
    }

}
