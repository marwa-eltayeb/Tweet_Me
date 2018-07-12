package com.example.marwa.tweetme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         /* fetching the username from LoginActivity */
        String username = getIntent().getStringExtra("username");
        TextView uname = (TextView) findViewById(R.id.TV_username);
        uname.setText(username);
    }


}
