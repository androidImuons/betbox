package com.lotus.ui.main.rightsidemenu.mybets.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.AccountStatementModel;

import java.util.List;

public class AccountStatementAdapter extends BaseRecycleAdapter {

    private List<AccountStatementModel> list;

    public AccountStatementAdapter(List<AccountStatementModel> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        isForDesign = false;
        return new ViewHolder(inflateLayout(R.layout.item_account_statement));
    }

    @Override
    public int getDataCount() {
        return list != null ? list.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_s_no;
        TextView tv_date;
        TextView tv_narration;
        TextView tv_credit;
        TextView tv_debit;
        TextView tv_balance;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_narration = itemView.findViewById(R.id.tv_narration);
            tv_credit = itemView.findViewById(R.id.tv_credit);
            tv_debit = itemView.findViewById(R.id.tv_debit);
            tv_balance = itemView.findViewById(R.id.tv_balance);
        }

        @Override
        public void setData(int position) {
            AccountStatementModel model = list.get(position);
            tv_s_no.setText("" + (position + 1));
            tv_date.setText(model.getSdate());
            tv_narration.setText(model.getNarration());

            tv_credit.setText(model.getCredit());
            tv_debit.setText(model.getDebit());
            tv_balance.setText(model.getBalance());
        }
    }
}
