package com.example.quiz;

import java.util.Collections;
import java.util.List;

public class questionModel {
    private String question;
    private List<String> moption;
    private String answer;
    private boolean answer_status;
    private boolean bookMarked_status;
    private int radioButton_id;
    private boolean correct_Ans;

    public questionModel(String question, List<String> option, String answer,boolean answer_stat, boolean bookMarked_stat) {
        this.question = question;
        this.moption = option;
        this.answer = answer;
        this.answer_status = answer_stat;
        this.bookMarked_status = bookMarked_stat;
        this.radioButton_id = -1;
        this.correct_Ans = false;
        Collections.shuffle(moption);

    }

    public boolean isCorrect_Ans() {
        return correct_Ans;
    }



    public void setCorrect_Ans(boolean correct_Ans) {
        this.correct_Ans = correct_Ans;
    }

    public int getRadioButton_id() {
        return radioButton_id;
    }

    public void setRadioButton_id(int radioButton_id) {
        this.radioButton_id = radioButton_id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOption() {
        return moption;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isAnswer_status() {
        return answer_status;
    }

    public void setAnswer_status(boolean answer_status) {
        this.answer_status = answer_status;
    }

    public boolean isBookMarked_status() {
        return bookMarked_status;
    }

    public void setBookMarked_status(boolean bookMarked_status) {
        this.bookMarked_status = bookMarked_status;
    }
}
