package com.lotus.ui.main.leftsidemenu.competitionDetail.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.CompetitionDetailModel;

import java.util.List;

public class CompetitionChildAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<CompetitionDetailModel.MatchData> list;

    public CompetitionChildAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_competitions_child));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void updateData(List<CompetitionDetailModel.MatchData> match) {
        this.list = match;
        notifyDataSetChanged();
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tv_match_name;
        TextView tv_date;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_match_name = itemView.findViewById(R.id.tv_match_name);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            CompetitionDetailModel.MatchData matchData = list.get(position);
            tv_match_name.setText(matchData.getMatchName());
            tv_date.setText(matchData.getFormattedMatchdate());
        }
    }
}
