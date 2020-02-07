package com.lotus.ui.main.rightsidemenu.transferstatement.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.LedgerModel;

import java.util.List;

public class LedgerAdapter extends BaseRecycleAdapter {

    private List<LedgerModel> list;

    public LedgerAdapter(List<LedgerModel> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        isForDesign = false;
        return new ViewHolder(inflateLayout(R.layout.item_ledger));
    }

    @Override
    public int getDataCount() {
        return list != null ? list.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_s_no;
        TextView tv_date;
        TextView tv_description;
        TextView tv_credit;
        TextView tv_debit;
        TextView tv_balance;
        TextView tv_id;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_description = itemView.findViewById(R.id.tv_description);

            tv_credit = itemView.findViewById(R.id.tv_credit);
            tv_debit = itemView.findViewById(R.id.tv_debit);
            tv_balance = itemView.findViewById(R.id.tv_balance);
            tv_id = itemView.findViewById(R.id.tv_id);
        }

        @Override
        public void setData(int position) {
            LedgerModel model = list.get(position);
            tv_s_no.setText(model.getSrNo());
            tv_date.setText(model.getEDate());
            tv_description.setText(model.getNarration());

            tv_credit.setText(model.getCreditText());
            tv_debit.setText(isValidString(model.getDebitText()) ? model.getDebitText() : "0");
            tv_balance.setText(isValidString(model.getBalance()) ? model.getBalance() : "0");
            tv_id.setText(isValidString(model.getMstcode()) ? model.getMstcode() : model.getMstCode());
        }
    }
}
