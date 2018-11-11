package com.example.socialapp.miscellaneous;

import java.util.InputMismatchException;

public class Menu {

    public static void makeMenu(int amount, String... message){
        if(amount == message.length){
            for(int i = 0; i < amount; i++){
                System.out.println((i+1) + " - " + message[i]);
            }
        }else{
            throw new InputMismatchException("amount must be the same as the amount of messages");
        }
    }
}
