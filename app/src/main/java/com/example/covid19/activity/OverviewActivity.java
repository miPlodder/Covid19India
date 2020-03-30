package com.example.covid19.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.example.covid19.R;
import com.example.covid19.adapter.CovidDetailsAdapter;
import com.example.covid19.adapter.CovidDetailsTableDataSourceImpl;
import com.example.covid19.model.dataJson.DataJSON;
import com.example.covid19.model.stateDistrictWiseJson.DistrictData;
import com.example.covid19.model.stateDistrictWiseJson.StateDistrictWiseJson;
import com.example.covid19.presenter.OverviewPresenter;

import java.net.InetAddress;

public class OverviewActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private AdaptiveTableLayout tableLayout;
    private CovidDetailsAdapter tableAdapter;
    private OverviewPresenter presenter;
    private CovidDetailsTableDataSourceImpl covidDetailsTableDataSource;
    private DataJSON dataJSON = null;
    private StateDistrictWiseJson stateDistrictWiseJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        tableLayout = findViewById(R.id.tableLayout);

        presenter = new OverviewPresenter(this);

        presenter.getDataJSON();
        presenter.getStateDistrictWiseJSON();

        if (!isInternetAvailable()) {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overview_menu, menu);
        return true;
    }

    public void setStateDistrictWiseJson(StateDistrictWiseJson stateDistrictWiseJson) {
        this.stateDistrictWiseJson = stateDistrictWiseJson;
    }

    public void setDashboard(DataJSON dataJSON) {
        ((TextView) findViewById(R.id.tvConfirmed)).setText(dataJSON.getStatewiseList().get(0).getConfirmed());
        ((TextView) findViewById(R.id.tvActive)).setText(dataJSON.getStatewiseList().get(0).getActive());
        ((TextView) findViewById(R.id.tvDeaths)).setText(dataJSON.getStatewiseList().get(0).getDeaths());
        ((TextView) findViewById(R.id.tvRecovered)).setText(dataJSON.getStatewiseList().get(0).getRecovered());
    }

    public void setDataToAdapter(DataJSON dataJSON) {
        this.dataJSON = dataJSON;
        setDashboard(this.dataJSON);
        covidDetailsTableDataSource = new CovidDetailsTableDataSourceImpl(this.dataJSON);
        tableAdapter = new CovidDetailsAdapter(this, covidDetailsTableDataSource);
        tableAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int row, int column) {
            }

            @Override
            public void onRowHeaderClick(int row) {
                if (stateDistrictWiseJson != null) {
                    String state = dataJSON.getStatewiseList().get(row).getState();
                    DistrictData districtData = stateDistrictWiseJson.getStateDistrictMap().get(state);
                    showMaterialDialog(districtData);
                }
            }

            @Override
            public void onColumnHeaderClick(int column) {
            }

            @Override
            public void onLeftTopHeaderClick() {
            }
        });
        tableLayout.setAdapter(tableAdapter);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showMaterialDialog(DistrictData districtData) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_districy_details, null);

        TableLayout tableLayout = view.findViewById(R.id.tlDistricts);
        tableLayout.removeAllViews();

        int largeFontSize = (int) getResources().getDimension(R.dimen.large_font);
        int smallFontSize = (int) getResources().getDimension(R.dimen.small_font);

        LinearLayout llHeader = new LinearLayout(this);
        LinearLayout.LayoutParams llParamsHeader = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        llHeader.setOrientation(LinearLayout.HORIZONTAL);
        llHeader.setWeightSum(2);

        TextView tvDistrictHeader = new TextView(this);
        tvDistrictHeader.setLayoutParams(new TableLayout.LayoutParams(0,
                TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        tvDistrictHeader.setGravity(Gravity.LEFT);
        tvDistrictHeader.setPadding(5, 15, 0, 15);
        //tvDistrict.setBackgroundColor(Color.parseColor("#f8f8f8"));
        tvDistrictHeader.setGravity(Gravity.CENTER);
        tvDistrictHeader.setText("District");
        tvDistrictHeader.setTypeface(null, Typeface.BOLD);
        tvDistrictHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeFontSize);

        TextView tvConfirmedHeader = new TextView(this);
        tvConfirmedHeader.setLayoutParams(new TableLayout.LayoutParams(0,
                TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));
        tvConfirmedHeader.setTextSize(TypedValue.COMPLEX_UNIT_PX, largeFontSize);
        tvConfirmedHeader.setTypeface(null, Typeface.BOLD);
        tvConfirmedHeader.setGravity(Gravity.CENTER);
        tvConfirmedHeader.setPadding(5, 15, 0, 15);
        tvConfirmedHeader.setBackgroundColor(Color.parseColor("#f8f8f8"));
        //tvConfirmed.setTextColor(Color.parseColor("#f8f8f8"));
        tvConfirmedHeader.setText("Confirmed");

        TableRow trHeader = new TableRow(this);
        TableLayout.LayoutParams tlHeaderParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        tlHeaderParams.setMargins(5, 5, 5, 5);

        llHeader.addView(tvDistrictHeader);
        llHeader.addView(tvConfirmedHeader);

        trHeader.addView(llHeader);

        tableLayout.addView(trHeader, tlHeaderParams);


        for (String key : districtData.getDistrictDataMap().keySet()) {
            String confirmed = districtData.getDistrictDataMap().get(key).getConfirmed();

            LinearLayout ll = new LinearLayout(this);
            LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(2);

            TextView tvDistrict = new TextView(this);
            tvDistrict.setLayoutParams(new TableLayout.LayoutParams(0,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            tvDistrict.setGravity(Gravity.LEFT);
            tvDistrict.setPadding(5, 15, 0, 15);
            tvDistrict.setGravity(Gravity.CENTER);
            tvDistrict.setText(key);
            tvDistrict.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallFontSize);

            TextView tvConfirmed = new TextView(this);
            tvConfirmed.setLayoutParams(new TableLayout.LayoutParams(0,
                    TableLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            tvConfirmed.setTextSize(TypedValue.COMPLEX_UNIT_PX, smallFontSize);
            tvConfirmed.setGravity(Gravity.CENTER);
            tvConfirmed.setPadding(5, 15, 0, 15);
            tvConfirmed.setBackgroundColor(Color.parseColor("#f8f8f8"));
            tvConfirmed.setText(confirmed);


            TableRow tr = new TableRow(this);
            TableLayout.LayoutParams tlParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT);
            tlParams.setMargins(5, 5, 5, 5);

            ll.addView(tvDistrict);
            ll.addView(tvConfirmed);

            tr.addView(ll);
            //tr.setLayoutParams(tlParams);
            //tr.addView(tvDistrict);
            //tr.addView(tvConfirmed);

            tableLayout.addView(tr, tlParams);

        }

/*        TextView tvDistrictHeader = view.findViewById(R.id.tvDistrictHeader);
        tvDistrictHeader.setText("District" + "                                " + "Confirmed" + "\n");

        TextView tvDetails = view.findViewById(R.id.tvDetails);
        tvDetails.setText("");
        for (String key : districtData.getDistrictDataMap().keySet()) {

            *//*for (int i = key.length(); i < 40; i++) {
                tvDetails.append(" ");
            }*//*
            StringBuffer confirmed = new StringBuffer(100);
            confirmed.append(key);

            Log.d(TAG, "showMaterialDialog1: ----------> " + (60 - confirmed.length()));
            for (int i = confirmed.length(); i < 60; i++) {
                Log.d(TAG, "showMaterialDialog2: " + i);
                confirmed.append("i");
            }
            //confirmed.append(districtData.getDistrictDataMap().get(key).getConfirmed() + "\n", 50, 50 + districtData.getDistrictDataMap().get(key).getConfirmed().length() + 1);
            confirmed.append(districtData.getDistrictDataMap().get(key).getConfirmed());
            confirmed.append("\n");
            tvDetails.append(confirmed.toString());
        }*/
        /*LinearLayout llDetails = view.findViewById(R.id.llDetails);

        for (String key : districtData.getDistrictDataMap().keySet()) {
            TextView tvDistrict =
        }*/

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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

    private void showPaymentAppsOption() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse("https://gpay.app.goo.gl/38NkZb"));
        startActivity(browserIntent);
    }

    private void showCoronaHelpDesk() {
        Uri uri = Uri.parse("smsto:" + "9013151515");
        Intent whatsappIntent = new Intent(Intent.ACTION_SENDTO, uri);
        whatsappIntent.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(whatsappIntent, ""));
    }

    private void showLinksActivity() {
        Intent linkIntent = new Intent(this, LinksActivity.class);
        startActivity(linkIntent);
    }

    public boolean isInternetAvailable() {
        final ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connMgr != null) {
            NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

            if (activeNetworkInfo != null) { // connected to the internet
                // connected to the mobile provider's data plan
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    return true;
                } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            }
        }
        return false;
    }

}
