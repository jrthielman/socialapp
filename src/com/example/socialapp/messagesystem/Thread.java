package com.example.socialapp.messagesystem;

import com.example.socialapp.interfaces.Closable;
import com.example.socialapp.miscellaneous.Choice;
import com.example.socialapp.miscellaneous.Styling;
import com.example.socialapp.accounts.Account;
import com.example.socialapp.exceptions.MustLoginException;

import java.util.ArrayList;
import java.util.List;

public class Thread implements Closable{

    private String title;
    private boolean closed;
    private Account currentUser;

    private List<Conversation> conversationList = new ArrayList<>();

    public Thread(String title) {
        this.title = title;
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

    public void showThreadInfo(){
        System.out.println(closed ? "Thread title: " + title + " [CLOSED]" +
                "\nAmount of conversations: " + conversationList.size() : "Thread title: " + title +
                "\nAmount of conversations: " + conversationList.size());
    }

    public boolean addConversation(Conversation conversation){
        if(!conversationList.contains(conversation)){
            conversationList.add(conversation);
            return true;
        }
        System.out.println("This conversation title already exists");
        return false;
    }

    public void showConversations(Account account) throws MustLoginException{
        currentUser = account;
        Styling.separationLine(4);
        System.out.println("Thread title [" + this.title + "]");
        Styling.separationLine(3);
        System.out.println("Available conversations: ");
        Styling.separationLine(9);
        for(Conversation conversation : conversationList){
            conversation.showConversationInfo();
            Styling.separationLine(5);
        }
        addOrView();
    }

    private void addOrView() throws MustLoginException{
        if(Choice.exitChoice(this.title).equalsIgnoreCase("n")){
            while(true){
                String input = Choice.viewOrAdd("Conversation");
                if(input.equalsIgnoreCase("add")){
                    if(currentUser != null){
                        while(true){
                            if(addConversation(new Conversation(Choice.listen("Title: ")))){
                                System.out.println("Conversation added.");
                                showConversations(currentUser);
                                return;
                            }
                        }
                    }else {
                        throw new MustLoginException();
                    }
                }else if(input.equalsIgnoreCase("view")){
                    if(conversationList.isEmpty()){
                        System.out.println("Conversation list is empty!");
                    }else{
                        navigateConversations();
                    }
                }else{
                    input = Choice.exitChoice(this.title);
                    if(input.equalsIgnoreCase("y")){
                        break;
                    }
                }
            }
        }
    }

    public void navigateConversations() throws MustLoginException{
        while(true) {
            String input = Choice.choose("conversation");
            for (Conversation conversation : conversationList) {
                if (conversation.getTitle().equalsIgnoreCase(input)) {
                    if(conversation.isClosed()){
                        System.out.println("This conversation is closed");
                    }else{
                        conversation.showMessages(currentUser);
                    }
                    break;
                }
            }
            input = Choice.exitChoice(this.title);
            if(input.equalsIgnoreCase("y")){
                break;
            }else{
                showConversations(currentUser);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Thread thread = (Thread) o;

        return title.equals(thread.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    public String getTitle() {
        return title;
    }

    public boolean isClosed() {
        return closed;
    }
}
