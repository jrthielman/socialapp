package com.example.socialapp.messagesystem;

import com.example.socialapp.accounts.User;
import com.example.socialapp.accounts.VeteranUser;
import com.example.socialapp.interfaces.Closable;
import com.example.socialapp.miscellaneous.Choice;
import com.example.socialapp.miscellaneous.Styling;
import com.example.socialapp.accounts.Account;
import com.example.socialapp.exceptions.MustLoginException;

import java.util.ArrayList;
import java.util.List;

public class Conversation implements Closable {

    private String title;
    private boolean closed;
    private Account currentUser;

    private List<Message> messageList = new ArrayList<>();

    public Conversation(String title) {
        this.title = title;
    }

    public void addMessage(String message) throws MustLoginException{
        if(currentUser == null){
            throw new MustLoginException();
        }else{
            if(currentUser instanceof User){
                messageList.add(((User) currentUser).postMessage(message));
            }else{
                System.out.println("admin's cannot post messages");
            }
        }
    }

    @Override
    public boolean close() {
        if(closed){
            return false;
        }
        closed = true;
        return true;
    }

    @Override
    public boolean open() {
        if(!closed){
            return false;
        }
        closed = false;
        return true;
    }

    public void showConversationInfo(){
        System.out.println(closed ? "Conversation title: " + title + " [CLOSED]" +
                "\nAmount of messages: " + messageList.size() : "Conversation title: " + title +
        "\nAmount of messages: " + messageList.size());
    }

    private void printMessages(){
        int messageCounter = 0;
        for(Message m : messageList){
            System.out.println("Message #" + ++messageCounter);
            m.showMessageInfo();
            m.showReplies();
            Styling.separationLine(5);
            Styling.spacing(0);
        }
    }

    public void showMessages(Account account) throws MustLoginException{
        currentUser = account;
        Styling.separationLine(4);
        System.out.println("Conversation title: [" + this.title + "]");
        Styling.separationLine(3);
        if(messageList.size() > 0){
            printMessages();
        }else {
            System.out.println("No messages have yet been posted");
        }
        addOrReply();
    }

    private void addOrReply() throws MustLoginException{
        while(true){
            String input = Choice.listen("Add a message or reply to a message?\nb to return");
            if(input.equalsIgnoreCase("b")){
                break;
            }else if(input.equalsIgnoreCase("add")){
               addMessage(Choice.listen("new message: "));
                   printMessages();
           }else if(input.equalsIgnoreCase("reply")){
               while(true){
                   try{
                       input = Choice.listen("Which message number? ");
                       int number = Integer.parseInt(input);
                       if(number <= messageList.size()){
                           messageList.get(number-1).addReply(currentUser,Choice.listen("Reply message: "));
                           System.out.println("reply added.");
                           printMessages();
                           break;
                       }else{
                           System.out.println("this message doesn't exist");
                       }
                   }catch(NumberFormatException nfe){
                       System.out.println("please enter a number");
                   }
               }
           }
        }
    }

    public String getTitle() {
        return title;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }
}
