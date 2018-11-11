package com.example.socialapp;

import com.example.socialapp.accounts.Account;
import com.example.socialapp.accounts.Admin;
import com.example.socialapp.accounts.User;
import com.example.socialapp.accounts.VeteranUser;
import com.example.socialapp.data.AccountDatabase;
import com.example.socialapp.exceptions.MustLoginException;
import com.example.socialapp.exceptions.NotAuthorizedException;
import com.example.socialapp.messagesystem.Thread;
import com.example.socialapp.miscellaneous.Choice;
import com.example.socialapp.miscellaneous.Menu;

import java.util.ArrayList;
import java.util.List;

public class AdminWindow {

    private Admin currentAdmin;
    private MainWindow mainWindow;
    private List<Thread> threadList = MainWindow.getThreadList();

    public AdminWindow(Admin admin, MainWindow mainWindow) throws MustLoginException {
        currentAdmin = admin;
        this.mainWindow = mainWindow;
        showOptions();
    }

    private void refreshThreadlist(){
        threadList = MainWindow.getThreadList();
    }

    private void showOptions() throws MustLoginException{
        while(true){
            System.out.println("Admin mode: \n\n");
            Menu.makeMenu(2, "show users", "thread options");
            try{
                int choice = Integer.parseInt(Choice.listen("choice: "));
                switch(choice){
                    case 1:
                        if(!userOptions()){
                            System.out.println("No users available");
                        }
                        break;
                    case 2:
                        threadOptions();
                        break;
                }
            }catch(NumberFormatException nfe){
                System.out.println("Incorrect input");
            }
            if(exit("out of admin mode")){
                break;
            }
        }
    }

    private boolean handleThreadInput(int choice) throws IllegalArgumentException{
        if(choice == 1 || choice == 2){
            return true;
        }
        throw new IllegalArgumentException();
    }

    private Thread closeOpenThread(){
        if(printAvailableThreads()){
            String threadTitle = Choice.listen("Select a thread: ");
            for(Thread thread : threadList){
                if(thread.getTitle().equalsIgnoreCase(threadTitle)){
                    return thread;
                }
            }
            System.out.println("thread doesn't exist");
            return null;
        }
        System.out.println("No threads available");
        return null;
    }

    private boolean printAvailableThreads(){
        refreshThreadlist();
        if(!threadList.isEmpty()){
            for(Thread thread : threadList){
                thread.showThreadInfo();
            }
            return true;
        }
        return false;
    }

    private void threadOptions() throws MustLoginException{
        while(true){
            Menu.makeMenu(2, "add/view", "close/open");
            try{
                int choice = Integer.parseInt(Choice.listen("choice: "));
                if(handleThreadInput(choice)){
                    switch(choice){
                        case 1:
                            mainWindow.showThreads();
                            break;
                        case 2:
                            Thread thread = closeOpenThread();
                            if(thread != null){
                               String inputUser =  Choice.listen(thread.isClosed() ? "Do you want to open this thread? y/n\n" :
                                        "Do you want to close this thread? y/n\n");
                               if(inputUser.equalsIgnoreCase("y") || inputUser.equalsIgnoreCase("yes")){
                                   if(thread.isClosed()){
                                       thread.open();
                                       System.out.println("thread opened");
                                   }else{
                                       thread.close();
                                       System.out.println("thread closed");
                                   }
                               }
                            }
                            break;
                    }
                }
            }catch(NumberFormatException nfe){
                System.out.println("Invalid input.");
            }catch(IllegalArgumentException iae){
                System.out.println("This is not an option.");
            }
            if(exit("Thread options")){
                break;
            }
        }
    }

    private List<VeteranUser> showAndGetVeteranUsers(){
        System.out.println("Veteran users");
        List<VeteranUser> veteranList = new ArrayList<>();
        for(Account acc : AccountDatabase.getDatabase().getAccountlist()){
            if(acc instanceof VeteranUser){
                VeteranUser veteran = (VeteranUser)acc;
                veteranList.add(veteran);
                System.out.println(acc.getUsername());
            }
        }
        return veteranList;
    }

    private void handleUserOptions(int input, Account user){
        if(input >= 1 && input <= 4){
            switch(input){
                case 1:
                    if(((User)user).banUser()){
                        System.out.println(user.getUsername() + " has been banned");
                        break;
                    }
                    System.out.println(user.getUsername() + " is already banned");
                    break;
                case 2:
                    if(((User)user).unbanUser()){
                        System.out.println(user.getUsername() + " is unbanned");
                        break;
                    }
                    System.out.println(user.getUsername() + " is not banned");
                    break;
                case 3:
                    AccountDatabase.getDatabase().promoteUser(currentAdmin.promoteUser(user));
                    System.out.println(user.getUsername() + " is now a veteran user");
                    break;
                case 4:
                    List<VeteranUser> veterans = showAndGetVeteranUsers();
                    String username = Choice.listen("which user");
                    for(VeteranUser vu : veterans){
                        if(vu.getUsername().equalsIgnoreCase(username.trim())){
                            currentAdmin.raiseInfleunce(vu,2);
                        }
                    }
                    break;

            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    private boolean exit(String name){
        if(Choice.exitChoice(name).equalsIgnoreCase("y")){
            return true;
        }
        return false;
    }

    private boolean userOptions(){
        while(true){
            showUsers();
            String username = Choice.listen("Which user do you want to select: ");
            Account user = getUser(username);
            if(user != null) {
                Menu.makeMenu(4, "ban user", "unban user", "promote user", "raise influence");
                try{
                    int choice = Integer.parseInt(Choice.listen("choice: "));
                    handleUserOptions(choice, user);
                    return true;
                }catch(NumberFormatException nfe){
                    System.out.println("invalid input. Try again");
                }catch(IllegalArgumentException iae){
                    System.out.println("invalid choice.");
                }
            }else{
                System.out.println("user does not exist");
            }
            if(exit("user options")){
                break;
            }
        }
        return false;
    }

    private Account getUser(String username){
        showUsers();
        for(Account account : AccountDatabase.getDatabase().getAccountlist()){
            if(account.getUsername().equalsIgnoreCase(username.trim())){
                return account;
            }
        }
        return null;
    }

    private void showUsers(){
        for(Account account : AccountDatabase.getDatabase().getAccountlist()){
            System.out.println(account.getUsername());
        }
    }
}

