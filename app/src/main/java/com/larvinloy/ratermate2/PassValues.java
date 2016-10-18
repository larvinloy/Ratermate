package com.larvinloy.ratermate2;

/**
 * Created by andrewfinlayson on 17/10/2016.
 */

import java.math.BigInteger;
import java.util.ArrayList;

public class PassValues {

    static ArrayList<String> categories = new ArrayList<String>();

    static long sessionID;

    ArrayList<BigInteger> clientValues = new ArrayList<BigInteger>();


    //Adds to the categories array
    public void addCategory(String category)
    {
        categories.add(category);
    }

    //getter
    public ArrayList<String> getCategories()
    {
        return categories;
    }

    public void clearCategories()
    {
        categories.clear();
    }



    public void clearCLientValues()
    {
        clientValues.clear();
    }

    public void addSessionID(long sessionID)
    {
        this.sessionID = sessionID;
    }

    public long getSessionID()
    {
        return sessionID;
    }

    public void addClientValues(BigInteger n, BigInteger g)
    {

        clientValues.add(n);
        clientValues.add(g);

    }

    public ArrayList<BigInteger> getClientValues()
    {
        return clientValues;
    }







}