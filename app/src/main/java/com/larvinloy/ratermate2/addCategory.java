package com.larvinloy.ratermate2;

/**
 * Created by andrewfinlayson on 17/10/2016.
 */

import java.util.*;

public class addCategory {

    public ArrayList<String> categories = new ArrayList<String>();

    //Adds to the categories array
    public void addCategory(String category){
        categories.add(category);
    }

    //getter
    public ArrayList<String> getCategories(){
        return categories;
    }

}
