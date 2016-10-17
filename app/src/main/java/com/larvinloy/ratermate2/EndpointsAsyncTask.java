package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.example.larvinloy.myapplication.backend.quoteApi.QuoteApi;
import com.example.larvinloy.myapplication.backend.quoteApi.model.Quote;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class EndpointsAsyncTask extends AsyncTask<Void, Void, Session> {
    private static SessionApi myApiService = null;
    private Context context;

    EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Session doInBackground(Void... params) {
        if(myApiService == null) { // Only do this once
            SessionApi.Builder builder = new
                    SessionApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
// options for running against local devappserver
// — 10.0.2.2 is localhost’s IP address in Android emulator
// — turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?>    abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
// end options for devappserver

            myApiService = builder.build();
        }
        Session test = new Session();
        Session resp;
        try {
//            test.
//            test.setWho("TWO ");
//            test.setWhat("ONE");
            test.setModLength(1024);
            test.setG("1111111111111");
            test.setN("3333333333333");
            test.setModLength(1024);
            test.setPasskey("password");

            test.setCategories(new ArrayList<String>(){{
                add("Apples");
                add("Oranges");

            }});

            resp = myApiService.insert(test).execute();
            return resp;
        } catch (IOException e) {
            return test;
        }
    }

    @Override
    protected void onPostExecute(Session result) {

            Toast.makeText(context, String.valueOf(result.getSessionId()),
                    Toast.LENGTH_LONG).show();

    }
}
