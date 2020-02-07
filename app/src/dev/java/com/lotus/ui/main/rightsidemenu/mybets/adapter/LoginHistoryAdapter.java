package com.lotus.ui.main.rightsidemenu.mybets.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.LoginHistoryModel;

import java.util.List;

public class LoginHistoryAdapter extends BaseRecycleAdapter {

    private List<LoginHistoryModel> list;


    public LoginHistoryAdapter(List<LoginHistoryModel> list) {
        this.list = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        isForDesign = false;
        return new ViewHolder(inflateLayout(R.layout.item_login_history));
    }

    @Override
    public int getDataCount() {
        return list != null ? list.size() : 0;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_s_no;
        TextView tv_date;
        TextView tv_ip_address;
        TextView tv_device_info;
        TextView tv_browser_info;
        LinearLayout ll_device_info;
        LinearLayout ll_browserInfo;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_s_no = itemView.findViewById(R.id.tv_s_no);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_ip_address = itemView.findViewById(R.id.tv_ip_address);
            tv_device_info = itemView.findViewById(R.id.tv_device_info);
            tv_browser_info = itemView.findViewById(R.id.tv_browser_info);
            ll_device_info = itemView.findViewById(R.id.ll_device_info);
            ll_browserInfo = itemView.findViewById(R.id.ll_browserInfo);

        }

        @Override
        public void setData(int position) {
            LoginHistoryModel model = list.get(position);
            tv_s_no.setText("" + (position + 1));
            tv_date.setText(model.getLogstdt());

            tv_ip_address.setText(isValidString(model.getIpadress())
                    ? model.getIpadress() : "-");

            tv_device_info.setText(isValidString(model.getDevice_info())
                    ? model.getDevice_info() : "-");


            tv_browser_info.setText(isValidString(model.getBrowser_info())
                    ? model.getBrowser_info() : "-");

        }
    }
}
