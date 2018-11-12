package com.example.socialapp.accounts;

public class VeteranUser extends User {

    private int influenceLevel = 2;

    public VeteranUser(String username, String password){
        super(username,password);
    }

    public void reportUser(User user){
        if(user.report()){
            System.out.println(user.getUsername() + " has been reported");
        }else{
            System.out.println(user.getUsername() + " has been banned for"
                    + user.getMAX_WARNING() + " days");
            user.banUser();
        }
    }

    public void rateUser(User user){
        user.raiseRating();
    }

    void raiseInfluence(Admin admin, int amount){
        System.out.println(admin.getUsername() + " has raised " + this.getUsername() +
                " his influence by " + amount);
        influenceLevel += amount;
    }

    public int getInfluenceLevel() {
        return influenceLevel;
    }
}
