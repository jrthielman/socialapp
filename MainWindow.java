package com.example.socialapp;

import com.example.socialapp.accounts.Account;
import com.example.socialapp.accounts.Admin;
import com.example.socialapp.accounts.User;
import com.example.socialapp.data.AccountDatabase;
import com.example.socialapp.exceptions.LoginFailedException;
import com.example.socialapp.exceptions.MustLoginException;
import com.example.socialapp.exceptions.NotAuthorizedException;
import com.example.socialapp.messagesystem.Thread;
import com.example.socialapp.miscellaneous.Choice;
import com.example.socialapp.miscellaneous.Styling;

import java.util.ArrayList;
import java.util.List;

public class MainWindow {

    private String applicationName;
    private Account currentUser;

    private static List<Thread> threadList = new ArrayList<>();
    private static List<Account> onlineUsers = new ArrayList<>();

    MainWindow(String applicationName){
        this.applicationName = applicationName;
    }

    public static void main(String[] args) {
        new MainWindow("DragonTalkZ").start();
    }

    public void start(){
        while(true){
            if(!userLogin()){
                if(Choice.listen("Do you want to logout? y/n: ").equalsIgnoreCase("y")){
                    currentUser.logout();
                    currentUser = null;
                    System.out.println("Goodbye!");
                    break;
                }
            }
        }
    }

    public void assignAdmin(){
        currentUser = new Admin();
    }

    private boolean adminLogin(String username) throws MustLoginException{
        currentUser = new Admin();
        Admin admin = (Admin)currentUser;
        if(admin.getUsername().equalsIgnoreCase(username.trim())){
            String password = Choice.listen("password: ");
            if(admin.getPassword().equalsIgnoreCase(password.trim())){
                new AdminWindow(admin, this);
                String input = Choice.listen("Do you want to login again? y/n");
                if(input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")){
                    throw new MustLoginException();
                }else {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean userLogin(){
        while(true){
            System.out.println("Login screen");
            Styling.separationLine(6);
            int amountOfLoops = 0;
            String username = Choice.listen("username: ").trim();

            try {
                if (!adminLogin(username)) {
                    for (Account account : AccountDatabase.getDatabase().getAccountlist()) {
                        amountOfLoops++;
                        if (account.getUsername().equalsIgnoreCase(username)) {
                            if(((User)account).isBanned()){
                                System.out.println("This user is banned");
                                if(Choice.exitChoice(this.applicationName).equalsIgnoreCase("y")){
                                    return false;
                                }
                                break;
                            }else{
                                String password = Choice.listen("password: ");
                                while (true) {
                                    try {
                                        account.login(password);
                                        break;
                                    } catch (LoginFailedException le) {
                                        System.out.println("This password does not match with the username");
                                        password = Choice.listen("Enter password again: ");
                                    }
                                }
                                currentUser = account;
                                onlineUsers.add(account);
                                System.out.println("You are logged in as " + account.getUsername());
                                try {
                                    showThreads();
                                    return false;
                                } catch (MustLoginException mle) {
                                    System.out.println("Please login");
                                }
                            }
                        }
                    }
                }
            }catch(MustLoginException mle){
                System.out.println("Please login");
            }
            if(amountOfLoops == AccountDatabase.getDatabase().getAccountlist().size()){
                System.out.println("We don't have an account with this username");
                String input = Choice.register();
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    registerAccount();
                }
                input = Choice.exitChoice(this.applicationName);
                if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("yes")) {
                    return false;
                }
            }
        }
    }

    public void registerAccount(){
        boolean registered;
        String username, password;

        while(true){
            System.out.println("Register");
            Styling.separationLine(6);

            username = Choice.listen("username: ");
            password = Choice.listen("password: ");

            registered = AccountDatabase.getDatabase().addAccount(username,password);
            if(registered){
                System.out.println("You have been registered.");
                break;
            }else {
                System.out.println("This username already exists\nTry again");
            }
        }
    }

    public boolean addThread(Thread thread) throws MustLoginException,NotAuthorizedException{
        if(currentUser instanceof Admin){
            if(!threadList.contains(thread)){
                threadList.add(thread);
                return true;
            }
            System.out.println(thread.getTitle() + " already exists");
            return false;
        }else if(currentUser == null){
            throw new MustLoginException();
        }else{
            throw new NotAuthorizedException();
        }
    }

    private void threadList(){
        for(Thread thread : threadList){
            thread.showThreadInfo();
        }
    }

    public void showThreads() throws MustLoginException{
        while(true){
            System.out.println("Available threads: ");
            Styling.separationLine(9);
            threadList();

            String input = Choice.exitChoice(currentUser instanceof Admin ? "to admin mode?" : this.applicationName);
            if(input.equalsIgnoreCase("n")){
                try{
                    addOrView();
                }catch(NotAuthorizedException nae){
                    System.out.println("You are not allowed to do this");
                }
            }else if(input.equalsIgnoreCase("y")){
                break;
            }
        }
    }

    private void addOrView() throws NotAuthorizedException, MustLoginException{
        while(true){
            String input = Choice.viewOrAdd("Thread");
            if(input.equalsIgnoreCase("add")){
                if(currentUser != null){
                    while(true){
                        if(addThread(new Thread(Choice.listen("Title: ")))){
                            System.out.println("Thread added.");
                            threadList();
                            break;
                        }
                    }
                }else {
                    throw new MustLoginException();
                }
            }else if (input.equalsIgnoreCase("view")){
                if(threadList.isEmpty()){
                    System.out.println("Thread list is empty!");
                }else{
                    navigateThreads();
                }
            }else{
                break;
            }
            System.out.println("press enter to return");
        }
    }

    public void navigateThreads() throws MustLoginException{
        while(true) {
            String input = Choice.choose("thread");
            for (Thread thread : threadList) {
                if (thread.getTitle().equalsIgnoreCase(input)) {
                    if(thread.isClosed()){
                        System.out.println("This thread is closed!");
                    }else{
                        thread.showConversations(currentUser);
                    }
                    break;
                }
            }
            input = Choice.exitChoice(this.applicationName);
            if(input.equalsIgnoreCase("y")){
                break;
            }else{
                threadList();
            }
        }
    }

    public String getApplicationName() {
        return applicationName;
    }

    public static List<Thread> getThreadList() {
        return threadList;
    }

    public static List<Account> getOnlineUsers() {
        return onlineUsers;
    }
}
