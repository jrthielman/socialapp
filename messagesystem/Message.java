package com.example.socialapp.messagesystem;

import com.example.socialapp.miscellaneous.Styling;
import com.example.socialapp.accounts.Account;
import com.example.socialapp.accounts.User;

import java.util.ArrayList;
import java.util.List;

public class Message {

    private Account messageOwner;
    private String userMessage;

    private List<Message> replies = new ArrayList<>();

    public Message(Account user, String userMessage) {
        this.userMessage = userMessage;
        this.messageOwner = user;
    }

    public void showMessageInfo(){
        messageFormat(messageOwner);
    }

    public void addReply(Account user, String message){
        replies.add(new Message(user, message));
    }

    public boolean showReplies(){
        Styling.separationLine(4);
        System.out.println("Replies");
        Styling.separationLine(7);
        if(replies.size() > 0){
            for(Message message : replies){
                messageFormat(message, message.getMessageOwner());
                Styling.separationLine(2);
            }
            return true;
        }
        System.out.println("no replies for this message");
        return false;
    }

    private void messageFormat(Message message, Account account){
        if(account instanceof User){
            User user = (User)account;
            System.out.println("\tUsername: " + user.getUsername() +
                    "\n\n\t" + message.getUserMessage() +
                    "\n\n\tMessages posted: " + user.getAmountOfPosts() +
                    "\n\tUser Rating: " + user.getUserRating());
        }else{
            System.out.println("\tUsername: " + account.getUsername() +
                    "\n\n\t" + message.getUserMessage());
        }

    }
    private void messageFormat(Account account){
        if(account instanceof User) {
            User user = (User)account;
            System.out.println("Username: " + user.getUsername() +
                    "\n\n" + this.userMessage +
                    "\n\nMessages posted: " + user.getAmountOfPosts() +
                    "\nUser rating: " + user.getUserRating());
        }else{
            System.out.println("Username: " + account.getUsername() +
                    "\n\n" + this.userMessage);
        }
    }

    public Account getMessageOwner() {
        return messageOwner;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public List<Message> getReplies() {
        return new ArrayList<>(replies);
    }
}
