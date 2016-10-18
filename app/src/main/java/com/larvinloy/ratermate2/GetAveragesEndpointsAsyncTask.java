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
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.larvinloy.ratermate2.logic.Paillier;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static com.larvinloy.ratermate2.PassValues.sessionID;

//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;

class GetAveragesEndpointsAsyncTask extends AsyncTask<Void, Void, Session>
{
    private static SessionApi myApiService = null;
    private Context context;

    MainActivity mActivity;

    GetAveragesEndpointsAsyncTask(MainActivity activity) {
        mActivity = activity;
    }

    GetAveragesEndpointsAsyncTask(Context context) {
        this.context = context;
    }


    @Override
    protected Session doInBackground(Void... params) {

        sessionID = MainActivity.getSessionID();

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

//            SessionApi.Builder builder = new SessionApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null)
//                    .setRootUrl("https://ratermate.appspot.com/_ah/api/");

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

        Paillier paillier = Paillier.getInstance();
        List<String> encryptedAverages = new ArrayList<String>();
        encryptedAverages=result.getAverages();
        ArrayList<String> decryptedAverages = new ArrayList<String>();

        try {
            for(int i = 0 ; i < 2; i++)
            {
                BigInteger decryptedValue = paillier.decrypt(new BigInteger(encryptedAverages.get(i)));
                String decryptedValueInString = decryptedValue.toString();
                String decryptedValueWithDot = decryptedValueInString.substring(0, 1);
                decryptedValueWithDot += ".";
                decryptedValueWithDot += decryptedValueInString.substring(1,decryptedValueInString
                        .length());
                double decryptedValueInDouble = Double.valueOf(decryptedValueWithDot);
                int integerPart = (int)decryptedValueInDouble;
                double decimalPart = decryptedValueInDouble-integerPart;
                if(decimalPart > 0.99999)
                    decryptedValueInDouble = (double)integerPart + 1.0;

                decryptedAverages.add(String.valueOf(decryptedValueInDouble));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }


        //Changes TextView to display Value
        TextView rating1 = (TextView) mActivity.findViewById(R.id.ratingLabel1);
        TextView rating2 = (TextView) mActivity.findViewById(R.id.ratingLabel2);

        TextView category1 = (TextView) mActivity.findViewById(R.id.categoryLabel1);
        TextView category2 = (TextView) mActivity.findViewById(R.id.categoryLabel2);

        category1.setText(categories.get(0));
        category2.setText(categories.get(1));

        rating1.setText(decryptedAverages.get(0));
        rating2.setText(decryptedAverages.get(1));

//
//        myAwesomeTextView.setText(text);


    }
}