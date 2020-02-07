package com.lotus.ui.main.dialog.stackdialog.adapter;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.base.BaseRecycleAdapter;
import com.lotus.R;

import java.util.List;

public class StackAdapter extends BaseRecycleAdapter {

    private List<String> dataList;

    public StackAdapter(List<String> list) {
        this.dataList = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_stack));
    }

    @Override
    public int getDataCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public class ViewHolder extends BaseViewHolder {
        public EditText et_name;
        public RelativeLayout rl_view;

        public ViewHolder(View itemView) {
            super(itemView);
            et_name = itemView.findViewById(R.id.et_name);
            rl_view = itemView.findViewById(R.id.rl_view);

        }

        @Override
        public void setData(int position) {
            et_name.setText(dataList.get(position));
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) rl_view.getTag(), v);
        }
    }
}
