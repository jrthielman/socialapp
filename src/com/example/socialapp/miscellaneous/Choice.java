package com.example.socialapp.miscellaneous;

import java.util.Scanner;

public class Choice {

    private static Scanner scanner = new Scanner(System.in);

    public static String listen(String message){
        System.out.print(message);
        return scanner.nextLine();
    }

    public static String register(){
        System.out.println("Do you want to register? y/n");
        return scanner.nextLine();
    }

    public static String viewOrAdd(String name){
        System.out.println("Do you want to view or add a " + name);
        return scanner.nextLine();
    }

    public static String choose(String name){
        System.out.println("Which " + name +" do you want to visit");
        return scanner.nextLine();
    }

    public static String exitChoice(String name){
        System.out.println("Do you want to exit " + name + " y/n");
        return scanner.nextLine();
    }

    public static String exit(String name){
        System.out.println("Press enter to exit: " + name);
        return scanner.nextLine();
    }
}
