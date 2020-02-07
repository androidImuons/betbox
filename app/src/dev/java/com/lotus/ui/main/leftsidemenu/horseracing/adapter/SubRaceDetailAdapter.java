package com.lotus.ui.main.leftsidemenu.horseracing.adapter;

import android.view.View;

import com.base.BaseRecycleAdapter;
import com.lotus.R;

public class SubRaceDetailAdapter extends BaseRecycleAdapter {

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_race_detail));
    }

    @Override
    public int getDataCount() {
        return 3;
    }

    private class ViewHolder extends BaseViewHolder {


        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {
        }

        private void initializeCricketRecyclerView() {
        }
    }

}
