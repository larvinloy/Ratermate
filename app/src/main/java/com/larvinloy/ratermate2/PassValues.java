package com.larvinloy.ratermate2;

/**
 * Created by andrewfinlayson on 17/10/2016.
 */

import java.util.*;

public class PassValues {
    static ArrayList<String> categories = new ArrayList<String>();

    static long sessionID;


    //Adds to the categories array
    public void addCategory(String category){
        categories.add(category);
    }

    //getter
    public ArrayList<String> getCategories(){
        return categories;
    }

    public void clear(){
        categories.clear();
    }

    public void addSessionID(long sessionID){
        this.sessionID = sessionID;
    }

    public long getSessionID(){
        return sessionID;
    }

}