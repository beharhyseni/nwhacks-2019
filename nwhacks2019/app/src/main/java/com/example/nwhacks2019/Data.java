package com.example.nwhacks2019;

import android.widget.TextView;

import java.io.Serializable;

public class Data implements Serializable {

    private static volatile Data data_instance;

    private String text;


    public void setTextView(String s){
        this.text = s;
    }
    private Data() {
        if (data_instance != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Data getInstance() {
        if (data_instance == null) { //if there is no instance available... create new one
            synchronized (Data.class) {
                if (data_instance == null) data_instance = new Data();
            }
        }

        return data_instance;
    }
    public String getTextView(){
        return this.text;
    }

}
