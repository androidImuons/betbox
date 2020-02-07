package com.lotus.ui.main.matchDetail.live.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.MatchScoreModel;

import java.util.List;

public class ScoreTeamOneAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<MatchScoreModel.ResultBean.LastOverBean.BallsBeanX> list;

    public ScoreTeamOneAdapter(Context context) {
        isForDesign = false;
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_live_score));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(List<MatchScoreModel.ResultBean.LastOverBean.BallsBeanX> balls) {
        this.list = balls;
        notifyDataSetChanged();
    }

    private class ViewHolder extends BaseViewHolder {
        private ImageView iv_background;
        private TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_background = itemView.findViewById(R.id.iv_background);
            tv_name = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            MatchScoreModel.ResultBean.LastOverBean.BallsBeanX ballsBeanX = list.get(position);
            tv_name.setText(ballsBeanX.getValue());
            if (ballsBeanX.getType().equalsIgnoreCase("INFIELDBOUNDARY")) {
                if (ballsBeanX.getValue().equalsIgnoreCase("0"))
                    iv_background.setColorFilter(context.getResources().getColor(R.color.colorGray), android.graphics.PorterDuff.Mode.SRC_ATOP);
                else
                    iv_background.setColorFilter(context.getResources().getColor(R.color.color_green), android.graphics.PorterDuff.Mode.SRC_ATOP);
                if (ballsBeanX.getValue().equalsIgnoreCase("4")||ballsBeanX.getValue().equalsIgnoreCase("6"))
                    iv_background.setColorFilter(context.getResources().getColor(R.color.color_blue), android.graphics.PorterDuff.Mode.SRC_ATOP);
            } else if (ballsBeanX.getType().equalsIgnoreCase("WICKET")) {
                iv_background.setColorFilter(context.getResources().getColor(R.color.color_red), android.graphics.PorterDuff.Mode.SRC_ATOP);
            } else {
                iv_background.setColorFilter(context.getResources().getColor(R.color.color_yellow), android.graphics.PorterDuff.Mode.SRC_ATOP);
            }

        }
    }
}
