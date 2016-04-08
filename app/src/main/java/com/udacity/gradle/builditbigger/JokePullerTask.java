package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import in.divyamary.androidjokes.JokeActivity;
import in.divyamary.builditbigger.backend.jokeApi.JokeApi;


public class JokePullerTask extends AsyncTask<Void, Void, String> {

    private JokeApi myApiService = null;
    private ProgressBar spinner;
    private Context context;

    JokePullerTask(Context context, ProgressBar spinner){
        this.context = context;
        this.spinner = spinner;
    }

    @Override
    protected String doInBackground(Void... params) {
        String joke = null;
        if(myApiService == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - 192.168.56.1 is virtual box host only IP address for Genymotion emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
            try {
                joke = myApiService.getJoke().execute().getJoke();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return joke;

    }

    @Override
    protected void onPostExecute(String result) {
        spinner.setVisibility(View.GONE);
        Intent jokeIntent = new Intent(context, JokeActivity.class);
        jokeIntent.putExtra("joke", result);
        context.startActivity(jokeIntent);
    }
}
