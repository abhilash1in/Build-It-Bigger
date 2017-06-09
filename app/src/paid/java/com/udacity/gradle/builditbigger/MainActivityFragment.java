package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import in.goflo.jokeappandroidlibrary.JokeActivity;
import in.goflo.jokeappandroidlibrary.JokeIntent;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeIntent {



    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        Button tellJokeButton = (Button) root.findViewById(R.id.tellJokeButton);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(String joke) {
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getActivity(), JokeActivity.class);
                        intent.putExtra(JOKE_INTENT_KEY,joke);
                        startActivity(intent);
                    }
                }).execute(getActivity());
            }
        });
        return root;
    }
}
