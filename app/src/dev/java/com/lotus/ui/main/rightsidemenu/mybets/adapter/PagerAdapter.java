package com.lotus.ui.main.rightsidemenu.mybets.adapter;

import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;

public class PagerAdapter extends BaseRecycleAdapter {

    private int totalPages;
    private int currentPage;

    public void setTotalPages(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        notifyDataSetChanged();
    }

    public PagerAdapter() {

    }

    @Override
    public BaseViewHolder getViewHolder() {
        isForDesign = false;
        return new ViewHolder(inflateLayout(R.layout.item_pager));
    }

    @Override
    public int getDataCount() {
        return totalPages;
    }

    private class ViewHolder extends BaseViewHolder {

        TextView tv_current_page;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_current_page = itemView.findViewById(R.id.tv_current_page);
        }

        @Override
        public void setData(int position) {
            int data = position + 1;
            if (currentPage == data) {
                tv_current_page.setTextColor(getContext().getResources().getColor(R.color.color_yellow));
            } else {
                tv_current_page.setTextColor(getContext().getResources().getColor(R.color.et_text_color));
            }
            tv_current_page.setText("" + data);
            tv_current_page.setTag(position);
            tv_current_page.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            switch (v.getId()) {
                case R.id.tv_current_page:
                    performItemClick((Integer) tv_current_page.getTag(), tv_current_page);
                    break;
            }

        }
    }
}
