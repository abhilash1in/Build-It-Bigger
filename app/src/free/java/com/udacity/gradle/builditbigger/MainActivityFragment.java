package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import in.goflo.jokeappandroidlibrary.JokeActivity;
import in.goflo.jokeappandroidlibrary.JokeIntent;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeIntent {
    private final String TAG = getClass().getName();
    private InterstitialAd mInterstitialAd;
    private String retrievedJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        final ProgressBar progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        final Button tellJokeButton = (Button) root.findViewById(R.id.tellJokeButton);

        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d(TAG, getString(R.string.interstitial_ad_not_ready));
                }
                progressBar.setVisibility(View.VISIBLE);
                new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
                    @Override
                    public void processFinish(String joke) {
                        progressBar.setVisibility(View.GONE);
                        retrievedJoke = joke;
                    }
                }).execute(getActivity());
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Intent intent = new Intent(getActivity(), JokeActivity.class);
                intent.putExtra(JOKE_INTENT_KEY,retrievedJoke);
                startActivity(intent);
            }
        });
        return root;
    }
}
