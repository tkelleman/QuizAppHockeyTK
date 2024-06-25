package com.example.quizapphockeytk;

public class Question {
    private String qText;
    private  Boolean correctAns;

    public Question() {
        qText = "";
        correctAns = false;
    }

    public Question(String qText, Boolean correctAns) {
        this.qText = qText;
        this.correctAns = correctAns;
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

    @Override
    public String toString() {
        return "Question{" +
                "qText='" + qText + '\'' +
                ", correctAns=" + correctAns +
                '}';
    }
}
