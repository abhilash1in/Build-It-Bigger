package in.goflo.jokeappandroidlibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity implements JokeIntent{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra(JOKE_INTENT_KEY);
        if(joke != null && !joke.isEmpty()){
            ((TextView) findViewById(R.id.jokeTextView)).setText(joke);
        }
    }
}
