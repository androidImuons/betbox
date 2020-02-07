package com.lotus.ui.main.leftsidemenu.horseracing.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;

public class RaceAdapter extends BaseRecycleAdapter {

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_race));
    }

    @Override
    public int getDataCount() {
        return 10;
    }

    private class ViewHolder extends BaseViewHolder {


        private RecyclerView rv_race_detail;
        private ImageView iv_show_detail;
        private ImageView iv_hide_detail;

        public ViewHolder(View itemView) {
            super(itemView);
            rv_race_detail = itemView.findViewById(R.id.rv_race_detail);
            iv_show_detail = itemView.findViewById(R.id.iv_show_detail);
            iv_hide_detail = itemView.findViewById(R.id.iv_hide_detail);
        }

        @Override
        public void setData(int position) {
            initializeCricketRecyclerView();
            iv_show_detail.setOnClickListener(this);
            iv_hide_detail.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.iv_show_detail:
                    iv_hide_detail.setVisibility(View.VISIBLE);
                    iv_show_detail.setVisibility(View.GONE);
                    rv_race_detail.setVisibility(View.VISIBLE);
                    break;
                case R.id.iv_hide_detail:
                    iv_hide_detail.setVisibility(View.GONE);
                    iv_show_detail.setVisibility(View.VISIBLE);
                    rv_race_detail.setVisibility(View.GONE);
                    break;
            }
        }

        private void initializeCricketRecyclerView() {
            rv_race_detail.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            SubRaceDetailAdapter adapter = new SubRaceDetailAdapter();
            rv_race_detail.setAdapter(adapter);
        }
    }

}
