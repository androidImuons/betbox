package com.lotus.ui.main.dialog.plbymatchdialog.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.PLByMatchIdModel;

import java.util.List;

public class PLByMatchIdAdapter extends BaseRecycleAdapter {

    private List<PLByMatchIdModel> dataList;


    public PLByMatchIdAdapter(List<PLByMatchIdModel> list) {
        this.dataList = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_pl_by_match_id));
    }

    @Override
    public int getDataCount() {
        return dataList != null ? dataList.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {
        TextView tv_s_no;
        TextView tv_date;
        TextView tv_description;
        TextView tv_pl;
        TextView tv_comm;
        TextView iv_show_bet;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_pl = itemView.findViewById(R.id.tv_pl);
            tv_comm = itemView.findViewById(R.id.tv_comm);
            iv_show_bet = itemView.findViewById(R.id.iv_show_bet);

        }

        @Override
        public void setData(int position) {
            PLByMatchIdModel model = dataList.get(position);
            tv_s_no.setText("" + (position + 1));
            tv_date.setText(model.getCreatedOn());
            tv_description.setText(model.getEventName());
            tv_pl.setText(model.getPnL());
            tv_comm.setText(model.getComm());

            tv_pl.setTextColor(getContext().getResources().getColor(model.getTextColor(model.getPnL())));
            tv_comm.setTextColor(getContext().getResources().getColor(model.getTextColor(model.getComm())));

            iv_show_bet.setTag(position);
            iv_show_bet.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
             performItemClick((Integer) iv_show_bet.getTag(), iv_show_bet);
        }
    }
}
