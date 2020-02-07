package com.lotus.ui.main.dialog.datadialog.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;

import java.util.List;

public class DataAdapter<T> extends BaseRecycleAdapter {

    private List<T> dataList;

    public DataAdapter(List<T> list) {
        this.dataList = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_data));
    }

    @Override
    public int getDataCount() {
        return dataList != null ? dataList.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tv_name;
        private RelativeLayout rl_view;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_rider_name);
            rl_view = itemView.findViewById(R.id.rl_view);

        }

        @Override
        public void setData(int position) {
            tv_name.setText(dataList.get(position).toString());
            rl_view.setTag(position);
            rl_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) rl_view.getTag(), v);
        }
    }
}
