package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.larvinloy.ratermate2.PassValues.sessionID;

//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;

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
//            SessionApi.Builder builder = new
//                    SessionApi.Builder(AndroidHttp.newCompatibleTransport(),
//                    new AndroidJsonFactory(), null)
//// options for running against local devappserver
//// — 10.0.2.2 is localhost’s IP address in Android emulator
//// — turn off compression when running against local devappserver
//                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?>    abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

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