package com.example.covid19.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.covid19.R;

public class LinksActivity extends AppCompatActivity {

    private TextView tvHelplineNumber;
    private TextView tvMohfwSite;
    private TextView tvWhoSite;
    private TextView tvCdcLink;
    private TextView tvGlobalTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        tvHelplineNumber = findViewById(R.id.tvHelplineNumbers);
        tvHelplineNumber.setOnClickListener(getOnClickListener("https://www.mohfw.gov.in/coronvavirushelplinenumber.pdf"));
        tvMohfwSite = findViewById(R.id.tvMohfwSite);
        tvMohfwSite.setOnClickListener(getOnClickListener("https://www.mohfw.gov.in/"));
        tvWhoSite = findViewById(R.id.tvWhoSite);
        tvWhoSite.setOnClickListener(getOnClickListener("https://www.who.int/emergencies/diseases/novel-coronavirus-2019"));
        tvCdcLink = findViewById(R.id.tvCdcLink);
        tvCdcLink.setOnClickListener(getOnClickListener("https://www.cdc.gov/coronavirus/2019-ncov/faq.html"));
        tvGlobalTracker = findViewById(R.id.tvGlobalTracker);
        tvGlobalTracker.setOnClickListener(getOnClickListener("https://coronavirus.thebaselab.com/"));
    }

    private View.OnClickListener getOnClickListener(String url) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(browserIntent);
            }
        };
    }

}
