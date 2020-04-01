package covid19.example.covid19.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;

import covid19.example.covid19.adapter.CovidDetailsAdapter;
import covid19.example.covid19.adapter.CovidDetailsTableDataSourceImpl;
import covid19.example.covid19.model.dataJson.DataJSON;
import covid19.example.covid19.model.dataJson.Statewise;
import covid19.example.covid19.model.stateDistrictWiseJson.DistrictData;
import covid19.example.covid19.model.stateDistrictWiseJson.StateDistrictWiseJson;
import covid19.example.covid19.presenter.OverviewPresenter;

public class OverviewActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();
    private AdaptiveTableLayout tableLayout;
    private LinearLayout llError;
    private LinearLayout llContent;
    private CovidDetailsAdapter tableAdapter;
    private OverviewPresenter presenter;
    private CovidDetailsTableDataSourceImpl covidDetailsTableDataSource;
    private DataJSON dataJSON = null;
    private StateDistrictWiseJson stateDistrictWiseJson = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Fight Corona");
        }

        tableLayout = findViewById(R.id.tableLayout);
        llError = findViewById(R.id.llError);
        llContent = findViewById(R.id.llContent);

        presenter = new OverviewPresenter(this);

        presenter.getDataJSON();

        findViewById(R.id.btnError).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getDataJSON();
                presenter.getStateDistrictWiseJSON();
            }
        });

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
        List<Statewise> statewiseList = dataJSON.getStatewiseList();
        Statewise overallInformation = null;
        // proper check
        for (Statewise item : statewiseList) {
            if (item.getState() == "Total") {
                overallInformation = item;
                break;
            }
        }
        // double check, as Api is not consistent
        if (overallInformation == null) {
            overallInformation = statewiseList.get(0);
        }

        String confirmed = "<b>" + overallInformation.getConfirmed() + "</b> [" + overallInformation.getDelta().getConfirmed() + "]";
        ((TextView) findViewById(R.id.tvConfirmed)).setText(Html.fromHtml(confirmed));
        setFlag(overallInformation.getDelta().getConfirmed(), findViewById(R.id.ivImageConfirmed));

        String active = "<b>" + overallInformation.getActive() + "</b> [" + overallInformation.getDelta().getActive() + "]";
        ((TextView) findViewById(R.id.tvActive)).setText(Html.fromHtml(active));
        setFlag(overallInformation.getDelta().getActive(), findViewById(R.id.ivImageActive));

        String deaths = "<b>" + overallInformation.getDeaths() + "</b> [" + overallInformation.getDelta().getDeaths() + "]";
        ((TextView) findViewById(R.id.tvDeaths)).setText(Html.fromHtml(deaths));
        setFlag(overallInformation.getDelta().getDeaths(), findViewById(R.id.ivImageDeaths));

        String recovered = "<b>" + overallInformation.getRecovered() + "</b> [" + overallInformation.getDelta().getRecovered() + "]";
        ((TextView) findViewById(R.id.tvRecovered)).setText(Html.fromHtml(recovered));
        setFlag(overallInformation.getDelta().getRecovered(), findViewById(R.id.ivImageRecovered));
    }

    private void setFlag(int count, ImageView ivImage) {
        if (count > 0) {
            ivImage.setImageDrawable(getResources().getDrawable(R.drawable.trending_up_red_18dp));
        } else if (count < 0) {
            ivImage.setImageDrawable(getResources().getDrawable(R.drawable.trending_down_green_18dp));
        } else {
            ivImage.setImageDrawable(getResources().getDrawable(R.drawable.trending_flat_blue_18dp));
        }
    }

    public void setDataToAdapter(DataJSON dataJSON) {
        this.dataJSON = dataJSON;
        setDashboard(this.dataJSON);
        showContent();
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

    public void showContent() {
        llContent.setVisibility(View.VISIBLE);
        llError.setVisibility(View.GONE);
    }

    public void showError() {
        Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_LONG).show();
        llContent.setVisibility(View.GONE);
        llError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showMaterialDialog(DistrictData districtData) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_district_details, null);

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

            tableLayout.addView(tr, tlParams);

        }
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
            case R.id.item_my_message:
                this.showAboutMe();
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
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=919013151515");
        Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(whatsappIntent);
    }

    private void showLinksActivity() {
        Intent linkIntent = new Intent(this, LinksActivity.class);
        startActivity(linkIntent);
    }

    private void showAboutMe() {
        Intent aboutIntent = new Intent(this, MessageActivity.class);
        startActivity(aboutIntent);
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
