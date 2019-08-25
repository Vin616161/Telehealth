package com.example.myapplication.Bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;


public class DoctorData implements Serializable {
    int docId;
    private Drawable icon;
    private String text;

    public DoctorData(int id,Drawable icon, String text) {
        this.docId=id;
        this.icon = icon;
        this.text = text;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
    public int getDocId(){
        return docId;
    }
}