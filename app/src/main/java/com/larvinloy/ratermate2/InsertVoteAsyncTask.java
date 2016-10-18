package com.larvinloy.ratermate2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.larvinloy.myapplication.backend.;
import com.example.larvinloy.myapplication.backend.quoteApi.QuoteApi;
import com.example.larvinloy.myapplication.backend.quoteApi.model.Quote;
import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.larvinloy.ratermate2.logic.Paillier;
import com.larvinloy.ratermate2.logic.PublicEncryption;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.larvinloy.ratermate2.PassValues.sessionID;


/**
 * Created by andrewfinlayson on 19/10/2016.
 */

public class InsertVoteAsyncTask extends AsyncTask<Void, Void, Session> {

    private static SessionApi myApiService = null;
    private static PassValues add = new PassValues();
    private Context context;
    public ArrayList<String> categories = new ArrayList<String>();
    int modLength = 1024;
    PublicEncryption publicEncryption;





    @Override
    protected Session doInBackground(Void... params) {

        sessionID = MainActivity.getSessionID();

        ArrayList<BigInteger> clientvalues = add.getClientValues();

        ArrayList<Integer> votes = add.getVotes();

        BigInteger n = clientvalues.get(0);
        BigInteger g = clientvalues.get(1);

        publicEncryption = new PublicEncryption(modLength,n,g);

        int vote1 = votes.get(0);

        BigInteger v1 = BigInteger.valueOf(vote1);

        int vote2 = votes.get(1);

        BigInteger v2 = BigInteger.valueOf(vote2);

        try {
            BigInteger m1 = publicEncryption.encrypt(v1);
            BigInteger m2 = publicEncryption.encrypt(v2);
        } catch(Exception e){

        }




        if(myApiService == null) { // Only do this once

            QuoteApi.Builder builder = new QuoteApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null)
                    .setRootUrl("https://ratermate.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
        Vote test = new Vote();
        Quote resp;
        try {



            resp = myApiService.insert(test);
            return resp;
        } catch (IOException e) {
            return test;
        }
    }


//    @Override
//    protected void onPostExecute(Session result) {
//
//        List<String> categories = new ArrayList<String>();
//
//        categories = result.getCategories();
//
//        //Changes TextView to display Value
//        TextView text1 = (TextView) mActivity.findViewById(R.id.categoryVoteLabel1);
//        TextView text2 = (TextView) mActivity.findViewById(R.id.categoryVoteLabel2);
//
//        for(int i = 0; i < categories.size(); i++){
//            if(i == 0){
//                text1.setText(categories.get(i));
//            } else {
//                text2.setText(categories.get(i));
//            }
//        }
//
//    }

}
