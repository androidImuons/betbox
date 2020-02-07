package com.lotus.ui.main.matchDetail.market.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.IndianSessionModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FancyAdapter extends BaseRecycleAdapter {

    private Context context;
    private MatchDetailModel matchDetailModel;
    private List<IndianSessionModel> list = new ArrayList<>();
    private int isPosition = -1;

    public FancyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_match_fancy));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(MatchDetailModel matchDetailModel, DiffUtil.DiffResult diffResult) {
        this.matchDetailModel = matchDetailModel;

        this.list.clear();
        this.list.addAll(matchDetailModel.getSessions());

        diffResult.dispatchUpdatesTo(this);
    }

    public void setPosition(int position) {
        if (this.isPosition == position) {
            this.isPosition = -1;
        } else {
            this.isPosition = position;
        }
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tv_title;
        private LinearLayout ll_back;
        private TextView tv_back_price;
        private TextView tv_back_size;
        private LinearLayout ll_lay;
        private TextView tv_lay_price;
        private TextView tv_lay_size;
        private TextView tv_max_exposure;
        private ImageView iv_max_exposure;

        LinearLayout ll_message_lay;
        TextView tv_message;
        LinearLayout ll_fancy_position;
        RecyclerView recycler_view;
        FancyChildAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            ll_back = itemView.findViewById(R.id.ll_back);
            tv_back_price = itemView.findViewById(R.id.tv_back_price);
            tv_back_size = itemView.findViewById(R.id.tv_back_size);
            ll_lay = itemView.findViewById(R.id.ll_lay);
            tv_lay_price = itemView.findViewById(R.id.tv_lay_price);
            tv_lay_size = itemView.findViewById(R.id.tv_lay_size);
            ll_message_lay = itemView.findViewById(R.id.ll_message_lay);
            ll_message_lay.setVisibility(View.GONE);
            tv_message = itemView.findViewById(R.id.tv_message);
            tv_max_exposure = itemView.findViewById(R.id.tv_max_exposure);
            iv_max_exposure = itemView.findViewById(R.id.iv_max_exposure);
            ll_fancy_position = itemView.findViewById(R.id.ll_fancy_position);
            recycler_view = itemView.findViewById(R.id.recycler_view);
            ((MainActivity) context).updateViewVisibility(ll_fancy_position, View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);

        }

        @Override
        public void setData(int position) {
            if (list == null) return;
//            if (matchDetailModel.isMatchDisable()) {
//                ll_back.setOnClickListener(null);
//                ll_lay.setOnClickListener(null);
//                ll_back.setAlpha(0.5f);
//                ll_lay.setAlpha(0.5f);
//            } else {
//                ll_back.setOnClickListener(this);
//                ll_lay.setOnClickListener(this);
//                ll_back.setAlpha(1.0f);
//                ll_lay.setAlpha(1.0f);
//            }

            ll_back.setTag(position);
            ll_lay.setTag(position);
            iv_max_exposure.setTag(position);
            ll_back.setOnClickListener(this);
            ll_lay.setOnClickListener(this);


            IndianSessionModel indianSessionModel = list.get(position);
            if (indianSessionModel == null) return;
            adapter = new FancyChildAdapter(context);
            adapter.update(indianSessionModel.getFancy_position());
            recycler_view.setAdapter(adapter);
            tv_title.setText(indianSessionModel.getHeadName());
//            tv_status.setText(indianSessionModel.getGameStatus());

            if (indianSessionModel.getFancy_position() != null && indianSessionModel.getFancy_position().size() > 0) {
                iv_max_exposure.setOnClickListener(this);
                int max_exposure = indianSessionModel.getMax_exposure();
                tv_max_exposure.setText(String.valueOf(max_exposure));
                if (max_exposure > 0) {
                    tv_max_exposure.setTextColor(context.getResources().getColor(R.color.color_green));
                } else {
                    tv_max_exposure.setTextColor(context.getResources().getColor(R.color.color_red));
                }
                iv_max_exposure.setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

            } else {
                iv_max_exposure.setOnClickListener(null);
                tv_max_exposure.setText("");
                iv_max_exposure.setColorFilter(context.getResources().getColor(R.color.colorGray30), PorterDuff.Mode.SRC_IN);
            }

            if (isPosition == position) {
                ((MainActivity) context).updateViewVisibility(ll_fancy_position, View.VISIBLE);
            } else {
                ((MainActivity) context).updateViewVisibility(ll_fancy_position, View.GONE);
            }

            tv_back_price.setText(indianSessionModel.getSessInptYes());
            tv_back_size.setText(indianSessionModel.getYesValume());

            tv_lay_price.setText(indianSessionModel.getSessInptNo());
            tv_lay_size.setText(indianSessionModel.getNoValume());

            if (indianSessionModel.getActive() == 4 || indianSessionModel.getActive() == 0) {
                ll_message_lay.setVisibility(View.VISIBLE);
                if (indianSessionModel.getActive() == 4) {
                    tv_message.setText(indianSessionModel.getDisplayMsg());
                } else {
                    tv_message.setText("Ball Started");
                }
            } else {
                ll_message_lay.setVisibility(View.GONE);
            }
            tv_message.setTag(position);
            tv_message.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }
    }
}
