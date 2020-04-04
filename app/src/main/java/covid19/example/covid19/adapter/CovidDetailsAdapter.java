package covid19.example.covid19.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cleveroad.adaptivetablelayout.LinkedAdaptiveTableAdapter;
import com.cleveroad.adaptivetablelayout.ViewHolderImpl;
import com.example.covid19tracker.R;

import covid19.example.covid19.model.dataJson.Delta;

public class CovidDetailsAdapter extends LinkedAdaptiveTableAdapter<ViewHolderImpl> {

    private static final int[] COLORS = new int[]{
            0xffe62a10, 0xffe91e63, 0xff9c27b0, 0xff673ab7, 0xff3f51b5,
            0xff5677fc, 0xff03a9f4, 0xff00bcd4, 0xff009688, 0xff259b24,
            0xff8bc34a, 0xffcddc39, 0xffffeb3b, 0xffffc107, 0xffff9800, 0xffff5722};

    private final LayoutInflater mLayoutInflater;
    private final Context mcontext;
    private final CovidDetailsTableDataSource<String, String, String, String> mCovidDetailsTableDataSource;
    private final int mColumnWidth;
    private final int mRowHeight;
    private final int mHeaderHeight;
    private final int mHeaderWidth;
    private final String TAG = CovidDetailsAdapter.class.getClass().getSimpleName();

    public CovidDetailsAdapter(Context context, CovidDetailsTableDataSource<String, String, String, String> mCovidDetailsTableDataSource) {
        mcontext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mCovidDetailsTableDataSource = mCovidDetailsTableDataSource;
        Resources res = context.getResources();
        mColumnWidth = res.getDimensionPixelSize(R.dimen.column_width);
        mRowHeight = res.getDimensionPixelSize(R.dimen.row_height);
        mHeaderHeight = res.getDimensionPixelSize(R.dimen.column_header_height);
        mHeaderWidth = res.getDimensionPixelSize(R.dimen.row_header_width);
    }

    @Override
    public int getRowCount() {
        return mCovidDetailsTableDataSource.getRowsCount();
    }

    @Override
    public int getColumnCount() {
        return mCovidDetailsTableDataSource.getColumnsCount();
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateItemViewHolder(@NonNull ViewGroup parent) {
        return new CovidDetailsItemViewHolder(View.inflate(mcontext, R.layout.item_card, null));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateColumnHeaderViewHolder(@NonNull ViewGroup parent) {
        return new CovidDetailsHeaderColumnViewHolder(View.inflate(mcontext, R.layout.item_header_column, null));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateRowHeaderViewHolder(@NonNull ViewGroup parent) {
        return new CovidDetailsHeaderRowViewHolder(View.inflate(mcontext, R.layout.item_header_row, null));
    }

    @NonNull
    @Override
    public ViewHolderImpl onCreateLeftTopHeaderViewHolder(@NonNull ViewGroup parent) {
        return new CovidDetailsHeaderLeftTopViewHolder(View.inflate(mcontext, R.layout.item_header_left_top, null));
    }

    // CovidDetailsItemViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolderImpl viewHolder, int row, int column) {
        CovidDetailsItemViewHolder vh = (CovidDetailsItemViewHolder) viewHolder;

        String itemData = mCovidDetailsTableDataSource.getItemData(row, column);

        if (TextUtils.isEmpty(itemData)) {
            itemData = "";
        }

        itemData = itemData.trim();
        vh.tvText.setVisibility(View.VISIBLE);
        vh.ivImage.setVisibility(View.VISIBLE);

        Delta delta = ((CovidDetailsTableDataSourceImpl) mCovidDetailsTableDataSource).getJson().getStatewiseList().get(row).getDelta();

        switch (column) {
            case 1:
                vh.llItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setFlag(delta.getConfirmed(), vh);
                break;
            case 2:
                vh.llItem.setBackgroundColor(Color.parseColor("#f8f8f8"));
                setFlag(delta.getActive(), vh);
                break;
            case 3:
                vh.llItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
                setFlag(delta.getDeaths(), vh);
                break;
            case 4:
                vh.llItem.setBackgroundColor(Color.parseColor("#f8f8f8"));
                setFlag(delta.getRecovered(), vh);
                break;
        }
        vh.tvText.setText(itemData);
    }

    private void setFlag(int count, CovidDetailsItemViewHolder vh) {
        if (count > 0) {
            vh.ivImage.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.trending_up_red_18dp));
            //vh.tvText.setTypeface(null, Typeface.BOLD);
        } else if (count < 0) {
            vh.ivImage.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.trending_down_green_18dp));
        } else {
            vh.ivImage.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.trending_flat_blue_18dp));
        }
    }

    // CovidDetailsHeaderColumnViewHolder
    @Override
    public void onBindHeaderColumnViewHolder(@NonNull ViewHolderImpl viewHolder, int column) {
        CovidDetailsHeaderColumnViewHolder vh = (CovidDetailsHeaderColumnViewHolder) viewHolder;

        vh.tvText.setText(mCovidDetailsTableDataSource.getColumnHeaderData(column));  // skip left top header
        if (column == 2 || column == 4) {
            vh.llHeaderColumn.setBackgroundColor(Color.parseColor("#f8f8f8"));
        }
    }

    // CovidDetailsHeaderRowViewHolder
    @Override
    public void onBindHeaderRowViewHolder(@NonNull ViewHolderImpl viewHolder, int row) {
        CovidDetailsHeaderRowViewHolder vh = (CovidDetailsHeaderRowViewHolder) viewHolder;
        vh.tvText.setText(mCovidDetailsTableDataSource.getRowHeaderData(row));
    }

    // CovidDetailsHeaderLeftTopViewHolder
    @Override
    public void onBindLeftTopHeaderViewHolder(@NonNull ViewHolderImpl viewHolder) {
        CovidDetailsHeaderLeftTopViewHolder vh = (CovidDetailsHeaderLeftTopViewHolder) viewHolder;
        vh.tvText.setText(mCovidDetailsTableDataSource.getFirstHeaderData());
    }

    @Override
    public int getColumnWidth(int column) {
        return mColumnWidth;
    }

    @Override
    public int getHeaderColumnHeight() {
        return mHeaderHeight;
    }

    @Override
    public int getRowHeight(int row) {
        return mRowHeight;
    }

    @Override
    public int getHeaderRowWidth() {
        return mHeaderWidth;
    }

    /*---------------------------view holder------------------------------------------------------*/

    private static class CovidDetailsItemViewHolder extends ViewHolderImpl {
        TextView tvText;
        ImageView ivImage;
        LinearLayout llItem;

        private CovidDetailsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            ivImage = itemView.findViewById(R.id.ivImage);
            llItem = itemView.findViewById(R.id.llItem);
        }
    }

    private static class CovidDetailsHeaderColumnViewHolder extends ViewHolderImpl {
        TextView tvText;
        LinearLayout llHeaderColumn;

        private CovidDetailsHeaderColumnViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
            llHeaderColumn = itemView.findViewById(R.id.llHeaderColumn);
        }
    }

    private static class CovidDetailsHeaderRowViewHolder extends ViewHolderImpl {
        TextView tvText;

        private CovidDetailsHeaderRowViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
        }
    }

    private static class CovidDetailsHeaderLeftTopViewHolder extends ViewHolderImpl {
        TextView tvText;

        private CovidDetailsHeaderLeftTopViewHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tvText);
        }
    }
}
