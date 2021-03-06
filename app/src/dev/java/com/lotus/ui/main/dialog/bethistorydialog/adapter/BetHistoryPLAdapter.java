package com.lotus.ui.main.dialog.bethistorydialog.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.BetHistoryPLModel;

import java.util.List;

public class BetHistoryPLAdapter extends BaseRecycleAdapter {


    private List<BetHistoryPLModel> dataList;


    public BetHistoryPLAdapter(List<BetHistoryPLModel> list) {
        this.dataList = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_one_page_report));
    }

    @Override
    public int getDataCount() {
        return dataList != null ? dataList.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tv_s_no;
        TextView tv_date;
        TextView tv_description;
        TextView tv_selection_name;
        TextView tv_type;
        TextView tv_odds;
        TextView tv_stack;
        TextView tv_pl;
        TextView tv_profit;
        TextView tv_liability;
        TextView tv_status;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_selection_name = itemView.findViewById(R.id.tv_selection_name);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_odds = itemView.findViewById(R.id.tv_odds);
            tv_stack = itemView.findViewById(R.id.tv_stack);
            tv_pl = itemView.findViewById(R.id.tv_pl);
            tv_profit = itemView.findViewById(R.id.tv_profit);
            tv_liability = itemView.findViewById(R.id.tv_liability);
            tv_status = itemView.findViewById(R.id.tv_status);

        }

        @Override
        public void setData(int position) {
            BetHistoryPLModel model = dataList.get(position);

            tv_s_no.setText(model.getSrNo());
            tv_date.setText(model.getMstDate());
            tv_description.setText(model.getDescription());
            tv_selection_name.setText(model.getSelectionName());
            tv_type.setText(model.getType());
            tv_odds.setText(model.getOdds());
            tv_stack.setText(model.getStack());

            tv_pl.setText(model.getP_L());
            tv_profit.setText(isValidString(model.getProfit()) ? model.getProfit() : "0");
            tv_liability.setText(isValidString(model.getLiability()) ? model.getLiability() : "0");
            tv_status.setText(model.getSTATUS());

            tv_pl.setTextColor(getContext().getResources().getColor(model.getTextColor(model.getP_L())));
            tv_profit.setTextColor(getContext().getResources().getColor(model.getTextColor(model.getProfit())));
            tv_liability.setTextColor(getContext().getResources().getColor(model.getTextColor(model.getLiability())));
        }
    }
}
