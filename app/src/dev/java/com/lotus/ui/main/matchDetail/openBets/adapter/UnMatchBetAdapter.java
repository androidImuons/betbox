package com.lotus.ui.main.matchDetail.openBets.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.BetDataModel;

import java.util.ArrayList;
import java.util.List;

public class UnMatchBetAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<BetDataModel.BetUserData> list = new ArrayList<>();

    public UnMatchBetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_matched_bets));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }


    public void updateData(List<BetDataModel.BetUserData> list, DiffUtil.DiffResult unMatchedDiffResult) {
        this.list.clear();
        if (list != null)
            this.list.addAll(list);
        unMatchedDiffResult.dispatchUpdatesTo(this);
    }

    public BetDataModel.BetUserData getItem(int position) {
        if (list == null || list.size() == 0) return null;
        return list.get(position);
    }

    public List<BetDataModel.BetUserData> getList() {
        return list;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_runner_name;
        TextView tv_odds;
        TextView tv_stack;
        TextView tv_p_l;
        LinearLayout ll_market_bet_main;
        ImageView iv_dlt;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_runner_name = itemView.findViewById(R.id.tv_runner_name);
            tv_odds = itemView.findViewById(R.id.tv_odds);
            tv_stack = itemView.findViewById(R.id.tv_stack);
            tv_p_l = itemView.findViewById(R.id.tv_p_l);
            ll_market_bet_main = itemView.findViewById(R.id.ll_market_bet_main);
            iv_dlt = itemView.findViewById(R.id.iv_dlt);
        }

        @Override
        public void setData(int position) {
            iv_dlt.setTag(position);
            iv_dlt.setOnClickListener(this);

            if (list == null) return;
            BetDataModel.BetUserData betUserData = list.get(position);
            if (betUserData == null) return;
            tv_runner_name.setText(betUserData.getSelectionName() + "\n" + betUserData.getMstDate());
            tv_odds.setText(betUserData.getOdds());
            tv_stack.setText(betUserData.getStack());
            tv_p_l.setText(betUserData.getP_L());
            Log.e("1212121", "setData: unMatchedBet  " + position);
            if (betUserData.getIsBack().equals("0")) {
                ll_market_bet_main.setBackgroundColor(getContext().getResources().getColor(R.color.color_back));
            } else {
                ll_market_bet_main.setBackgroundColor(getContext().getResources().getColor(R.color.color_lay));
            }
            if (betUserData.getIsMatched().equalsIgnoreCase("1")) {
                iv_dlt.setVisibility(View.GONE);
            } else {
                iv_dlt.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }

    }
}
