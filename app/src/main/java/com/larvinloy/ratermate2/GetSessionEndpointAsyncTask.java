package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.larvinloy.myapplication.backend.quoteApi.QuoteApi;
import com.example.larvinloy.myapplication.backend.quoteApi.model.Quote;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
import com.larvinloy.ratermate2.logic.Paillier;
import com.larvinloy.ratermate2.logic.PublicEncryption;
//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;


import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.larvinloy.ratermate2.PassValues.sessionID;

class GetSessionEndpointsAsyncTask extends AsyncTask<Void, Void, Session> {
    private static SessionApi myApiService = null;
    private Context context;
    public ArrayList<String> categories = new ArrayList<String>();
    int modLength = 1024;
    Paillier paillier = new Paillier(modLength);
    PublicEncryption publicEncryption = new PublicEncryption(modLength,paillier.getN(),paillier.getG());
    private BigInteger n = publicEncryption.getN();
    private BigInteger g = publicEncryption.getG();


    GetSessionEndpointsAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected Session doInBackground(Void... params) {

        sessionID = MainActivity.getSessionID();

        if(myApiService == null) { // Only do this once

            SessionApi.Builder builder = new SessionApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null)
                    .setRootUrl("https://ratermate.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
        Session test = new Session();
        Session resp;
        try {
            resp = myApiService.get(sessionID).execute();
            return resp;
        } catch (IOException e) {
            return test;
        }
    }


    @Override
    protected void onPostExecute(Session result) {
//
        Toast.makeText(context, String.valueOf(result.getCategories()),
                Toast.LENGTH_LONG).show();

        //Changes TextView to display Value
//        TextView myAwesomeTextView = (TextView)findViewById(R.id.textDisplayCategory);
//
//        myAwesomeTextView.setText(text);


    }
}