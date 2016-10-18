package com.larvinloy.ratermate2;

/**
 * Created by larvinloy on 15/10/16.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.larvinloy.myapplication.backend.sessionApi.SessionApi;
import com.example.larvinloy.myapplication.backend.sessionApi.model.Session;
import com.example.larvinloy.myapplication.backend.voteApi.VoteApi;
import com.example.larvinloy.myapplication.backend.voteApi.model.Vote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.larvinloy.ratermate2.logic.Paillier;
import com.larvinloy.ratermate2.logic.PublicEncryption;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

//import com.example.larvinloy.myapplication.backend.sessionApi.
//import com.example.larvinloy.myapplication.backend.Quote;
//import com.example.larvinloy.myapplication.backend.QuoteEndpoint;

class InsertVoteAsyncTask extends AsyncTask<Void, Void, Vote>
{
    private static VoteApi voteApiService = null;
    private static SessionApi sessionApiService = null;
    private Context context;
    public ArrayList<String> categories = new ArrayList<String>();
    int modLength = 1024;
    Paillier paillier = Paillier.getInstance();
    PublicEncryption publicEncryption = new PublicEncryption(modLength,paillier.getN(),paillier.getG());
    private BigInteger n = publicEncryption.getN();
    private BigInteger g = publicEncryption.getG();


    InsertVoteAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Vote doInBackground(Void... params) {
        categories = MainActivity.getCategories();

        if(sessionApiService == null) { // Only do this once
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

            sessionApiService = builder.build();
        }

        if(voteApiService == null) { // Only do this once
            VoteApi.Builder builder = new
                    VoteApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
// options for running against local devappserver
// — 10.0.2.2 is localhost’s IP address in Android emulator
// — turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

//            SessionApi.Builder builder = new SessionApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null)
//                    .setRootUrl("https://ratermate.appspot.com/_ah/api/");

            voteApiService = builder.build();
        }

        Session session;
        PublicEncryption publicEncryption = new PublicEncryption(1,new BigInteger("0"),new BigInteger("0"));
        try {
            session = sessionApiService.getAverages(MainActivity.getSessionID()).execute();
            publicEncryption = new PublicEncryption(1024,
                    new BigInteger(session.getN()),new BigInteger(session.getG()));
        } catch (IOException e)
        {
        }

        Vote vote = new Vote();
        Vote test = new Vote();
        Vote resp;
        try {
            vote.setSessionId(MainActivity.getSessionID());
            ArrayList<String> votes = new ArrayList<String>();
            BigInteger vote1 = publicEncryption.encrypt(MainActivity.getClientValues().get(0));
            BigInteger vote2 = publicEncryption.encrypt(MainActivity.getClientValues().get(1));
            votes.add(publicEncryption.encrypt(vote1).toString());
            votes.add(publicEncryption.encrypt(vote2).toString());
//            test.setCategories(categories);
//            test.setG(g.toString());
//            test.setN(n.toString());
            vote.setModLength(modLength);


            resp = voteApiService.insert(vote).execute();
            //clear array list for next session
            return resp;
        } catch (IOException e) {
            return vote;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test;
    }


    @Override
    protected void onPostExecute(Vote result) {

        Toast.makeText(context, String.valueOf(result.getSessionId()),
                Toast.LENGTH_LONG).show();

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