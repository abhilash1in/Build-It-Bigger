package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import in.golfo.jokeapp.backend.myApi.MyApi;

/**
 * Created by abhilash1in on 8/6/17.
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myJokeApi = null;
    private Context context = null;

    public interface AsyncResponse{
        void processFinish(String joke);
    }

    public AsyncResponse delegate = null;

    public EndpointsAsyncTask(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(Context... params) {
        if(params.length > 0)
            context = params[0];
        if(myJokeApi == null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.1.6:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            if(context != null)
                builder.setRootUrl(context.getString(R.string.debug_url));
            myJokeApi = builder.build();
        }
        try {
            return myJokeApi.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if(delegate != null){
            delegate.processFinish(result);
            return;
        }
        if(context != null)
            Toast.makeText(context, context.getString(R.string.error) + ". Result: " + result, Toast.LENGTH_LONG).show();
    }
}
