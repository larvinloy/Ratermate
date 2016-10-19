package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.larvinloy.ratermate2.logic.Paillier;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;

class InsertSessionAsyncTask extends AsyncTask<Void, Void, Session> {
    private static SessionApi myApiService = null;

    public ArrayList<String> categories = new ArrayList<String>();
    int modLength = 1024;
    Paillier paillier = Paillier.getInstance();
    private BigInteger n = paillier.getN();
    private BigInteger g = paillier.getG();
    MainActivity mActivity;
    private Context context;



    InsertSessionAsyncTask(MainActivity activity)
    {
        this.mActivity = activity;
        this.context = activity;
    }


    @Override
    protected Session doInBackground(Void... params) {
        categories = MainActivity.getCategories();

        if(myApiService == null) { // Only do this once
//            SessionApi.Builder builder = new
//                    SessionApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//// options for running against local devappserver
//// — 10.0.2.2 is localhost’s IP address in Android emulator
//// — turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

            SessionApi.Builder builder = new SessionApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null)
                    .setRootUrl("https://ratermate.appspot.com/_ah/api/");

            myApiService = builder.build();
        }
        Session test = new Session();
        Session resp;
        try
        {
            test.setCategories(categories);
            test.setG(g.toString());
            test.setN(n.toString());
            test.setModLength(modLength);


            resp = myApiService.insert(test).execute();
            //clear array list for next session

            return resp;
        } catch (IOException e)
        {
            return test;
        }
    }


    @Override
    protected void onPostExecute(Session result) {


        TextView sessionId = (TextView) mActivity.findViewById(R.id.sessionId);
        if(sessionId!= null) {
            sessionId.setText(String.valueOf(result.getSessionId()));
            Toast.makeText(context,"Session Created!" ,
                    Toast.LENGTH_LONG).show();
            Button btn = (Button) mActivity.findViewById(R.id.buttonResults);
            btn.setEnabled(true);
            btn = (Button) mActivity.findViewById(R.id.buttonStart);
            btn.setEnabled(false);
        }
        else
        {
            Toast.makeText(context, "Failed",
                    Toast.LENGTH_LONG).show();
        }

    }
}

// options for running against local devappserver
//// — 10.0.2.2 is localhost’s IP address in Android emulator
//// — turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?>    abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

//    SessionApi.Builder builder = new
//                    SessionApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null);