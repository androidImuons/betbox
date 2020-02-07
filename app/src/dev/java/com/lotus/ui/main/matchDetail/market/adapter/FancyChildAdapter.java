package com.lotus.ui.main.matchDetail.market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.FancyPosition;

import java.util.List;

public class FancyChildAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<FancyPosition> list;

    public FancyChildAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_fancy_child));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(List<FancyPosition> fancyPositions) {
        this.list = fancyPositions;
        notifyDataSetChanged();
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tv_sessionInputYes;
        private TextView tv_ResultValue;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_sessionInputYes = itemView.findViewById(R.id.tv_sessionInputYes);
            tv_ResultValue = itemView.findViewById(R.id.tv_ResultValue);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            FancyPosition fancyPosition = list.get(position);
            tv_sessionInputYes.setText(fancyPosition.getSessInptYes());
            int resultValue = fancyPosition.getResultValue();
            if (resultValue > 0) {
                tv_ResultValue.setTextColor(context.getResources().getColor(R.color.color_green));
            } else {
                tv_ResultValue.setTextColor(context.getResources().getColor(R.color.color_red));
            }
            tv_ResultValue.setText(String.valueOf(resultValue));
        }
    }
}
