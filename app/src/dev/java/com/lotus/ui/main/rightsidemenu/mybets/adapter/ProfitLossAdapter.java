package com.lotus.ui.main.rightsidemenu.mybets.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.ProfitLossModel;

import java.util.List;

public class ProfitLossAdapter extends BaseRecycleAdapter {

    private List<ProfitLossModel> list;

    public ProfitLossAdapter(List<ProfitLossModel> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        isForDesign = false;
        return new ViewHolder(inflateLayout(R.layout.item_profit_loss));
    }

    @Override
    public int getDataCount() {
        return list != null ? list.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_s_no;
        TextView tv_date;
        TextView tv_description;
        TextView tv_pl;
        TextView tv_comm;
        ImageView iv_add;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);
            tv_pl = itemView.findViewById(R.id.tv_pl);
            tv_comm = itemView.findViewById(R.id.tv_comm);
            iv_add = itemView.findViewById(R.id.iv_add);
        }

        @Override
        public void setData(int position) {
            ProfitLossModel model = list.get(position);
            tv_s_no.setText("" + (position + 1));
            tv_date.setText(model.getSettle_date());
            tv_description.setText(model.getEventName());
            tv_pl.setTextColor(getContext().getResources().getColor(model.getAmountTextColor(model.getPlDouble())));
            tv_pl.setText(model.getPnL());
            tv_comm.setText(model.getComm());

            iv_add.setTag(position);
            iv_add.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            performItemClick((Integer) iv_add.getTag(), iv_add);
        }
    }
}
