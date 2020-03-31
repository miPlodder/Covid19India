package com.example.covid19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.covid19.R;

public class MessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My Message");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ((TextView) findViewById(R.id.tvMessage)).setText("Corona spreads in exponential chain, that means it transmits from one to few, then few to many and then many to many-many and so on.\n" +
                "\n" +
                "So, lets fight corona in same way, by starting a chain. Let's follow the lockdown properly and start the chain from us and transmit it to our family, then family to district, then district to state, then state to country and finally to a Corona-free India.\n" +
                "\n" +
                "Let us all unite as one nation.\n" +
                "\n" +
                "I have trigger the chain by following the lockdown properly.\n" +
                "\n" +
                "Now, it's your turn to continue the chain.\n" +
                "\n");

        ((TextView) findViewById(R.id.tvName)).setText("Saksham Handu AKA miplodder");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
