package com.example.socialapp.miscellaneous;

public class Styling {

    public static void spacing(int amount){
        if(amount < 3){
            switch(amount){
                case 1:
                    System.out.println("\n\n");
                    break;
                case 2:
                    System.out.println("\n\n\n");
                    break;
                default:
                    System.out.println();
                    break;
            }
        }else{
            throw new IllegalArgumentException();
        }
    }

    public static void separationLine(int type){
        if(type < 13){
            switch(type){
                case 1:
                    System.out.println("+++++++++++++++++++++++++++++++++");
                    break;
                case 2:
                    System.out.println("---------------------------------");
                    break;
                case 3:
                    System.out.println("==================================");
                    break;
                case 4:
                    System.out.println("__________________________________");
                    break;
                case 5:
                    System.out.println("..................................");
                    break;
                case 6:
                    System.out.println("***********************************");
                    break;
                case 7:
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 8:
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
                    break;
                case 9:
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    break;
                case 10:
                    System.out.println("||||||||||||||||||||||||||||||||||||");
                    break;
                case 11:
                    System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[");
                    break;
                case 12:
                    System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
                    break;
            }
        }else{
            throw new IllegalArgumentException();
        }
    }
}
