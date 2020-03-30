package com.example.covid19.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covid19.R;
import com.example.covid19.presenter.OverviewPresenter;

public class OverviewActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private OverviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        presenter = new OverviewPresenter(this);
        presenter.getDataJSON();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_donate:
                this.showPaymentAppsOption();
                break;
            case R.id.item_chat_with_us:
                this.showCoronaHelpDesk();
                break;
            case R.id.item_links:
                this.showLinksActivity();
                break;
            case R.id.item_contribute:
                Log.d(TAG, "onOptionsItemSelected: " + "item_contribute");
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void showPaymentAppsOption() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://gpay.app.goo.gl/38NkZb"));
        startActivity(browserIntent);
    }

    public void showCoronaHelpDesk() {
        Uri uri = Uri.parse("smsto:" + "9013151515");
        Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
        whatsappIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(whatsappIntent, ""));
    }

    public void showLinksActivity() {
        Intent linkIntent = new Intent(this, LinksActivity.class);
        startActivity(linkIntent);
    }
}
