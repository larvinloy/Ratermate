package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */
import android.app.Activity;
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

import static android.R.id.text1;
import static com.larvinloy.ratermate2.PassValues.sessionID;

class GetSessionEndpointsAsyncTask extends AsyncTask<Void, Void, Session>
{
    private static SessionApi myApiService = null;
    private Context context;

    MainActivity mActivity;

    GetSessionEndpointsAsyncTask(MainActivity activity) {
        mActivity = activity;
    }

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

        List<String> categories = new ArrayList<String>();

        categories = result.getCategories();

        //Changes TextView to display Value
        TextView text1 = (TextView) mActivity.findViewById(R.id.categoryVoteLabel1);
        TextView text2 = (TextView) mActivity.findViewById(R.id.categoryVoteLabel2);

        for(int i = 0; i < categories.size(); i++){
            if(i == 0){
                text1.setText(categories.get(i));
            } else {
                text2.setText(categories.get(i));
            }
        }
//
//        myAwesomeTextView.setText(text);


    }
}