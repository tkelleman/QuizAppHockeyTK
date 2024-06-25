package com.example.quizapphockeytk;

public class Question {
    private String qText;
    private  Boolean correctAns;
    private int qSound;

    public Question() {
        qText = "";
        correctAns = false;
    }

    public Question(String qText, Boolean correctAns, int qSound) {
        this.qText = qText;
        this.correctAns = correctAns;
        this.qSound = qSound;
    }

    public String getqText() {
        return qText;
    }

    public void setqText(String qText) {
        this.qText = qText;
    }

    public Boolean getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(Boolean correctAns) {
        this.correctAns = correctAns;
    }

    public int getqSound() {
        return qSound;
    }

    public void setqSound(int qSound) {
        this.qSound = qSound;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qText='" + qText + '\'' +
                ", correctAns=" + correctAns +
                '}';
    }
}
