package com.lotus.ui.main.leftsidemenu.cricket.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.SeriesListModel;

import java.util.List;

public class CompetitionsAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<SeriesListModel> list;

    public CompetitionsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_competitions));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void updateData(List<SeriesListModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tv_match_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_match_name = itemView.findViewById(R.id.tv_match_name);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            SeriesListModel seriesLst = list.get(position);
            tv_match_name.setText(seriesLst.getName());
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getId(), v);
        }
    }
}
