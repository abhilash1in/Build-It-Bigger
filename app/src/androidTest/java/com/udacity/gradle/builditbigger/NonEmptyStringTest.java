package com.udacity.gradle.builditbigger;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Matteo on 30/06/2015.
 */

@RunWith(AndroidJUnit4.class)
public class NonEmptyStringTest extends AndroidTestCase {

    private static final String LOG_TAG = "NonEmptyStringTest";

    @SuppressWarnings("unchecked")
    @Test
    public void nonEmptyStringTest() {
        // Testing that Async task successfully retrieves a non-empty string
        // You can test this from androidTest -> Run 'All Tests'
        Log.v("NonEmptyStringTest", "Running NonEmptyStringTest test");
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String joke) {
                Log.d(LOG_TAG,joke);
                assertNotNull("Null Joke",joke);
                assertFalse("Empty Joke",joke.isEmpty());
            }
        });
        endpointsAsyncTask.execute();

    }

}
