package com.example.quizapphockeytk;

public class PlayerScore {
    public String pName;
    public int pScore;
    //public String pDate;

    public PlayerScore(String pName, int pScore) {
        this.pName = pName;
        this.pScore = pScore;
        //this.pDate = pDate;
    }
    public PlayerScore() {
        // Initialize fields to default values if needed
    }

    // Getters and setters
    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpScore() {
        return pScore;
    }

    public void setpScore(int pScore) {
        this.pScore = pScore;
    }

    public String toString(){
        return "Name: :" + pName + " - " + "Score: " + pScore;
    }
}
