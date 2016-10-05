package com.example.andrewfinlayson.ratermate.model.interfaces;

/**
 * Created by andrewfinlayson on 5/10/2016.
 */

import java.math.BigInteger;
import java.util.HashMap;

import com.example.andrewfinlayson.ratermate.model.main.PublicEncryption;

public interface RatingEngine {

    void addToRating(String category, BigInteger rating);

    HashMap<String,String> getAverageRatings();

    void setAverageRatings(HashMap<String,String> averages,HashMap<String,Integer> voteCount);

    void caluclateAverageRatings();

    HashMap<String,Integer> getVoteCounts();

    void initConfiguration(HashMap<String,String> categories, PublicEncryption publicKey);

    PublicEncryption getPublicKey();

    HashMap<String,String> getCategories();

}
