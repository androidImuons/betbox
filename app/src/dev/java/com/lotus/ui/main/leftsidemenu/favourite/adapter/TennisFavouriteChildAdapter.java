package com.lotus.ui.main.leftsidemenu.favourite.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.AvailableToBackModel;
import com.lotus.model.AvailableToLayModel;
import com.lotus.model.BetSlipModel;
import com.lotus.model.MatchMarketModel;
import com.lotus.model.RunnersModel;

import java.util.ArrayList;
import java.util.List;

public class TennisFavouriteChildAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<RunnersModel> list = new ArrayList<>();
    MatchMarketModel matchMarketModel;


    public TennisFavouriteChildAdapter(Context context, MatchMarketModel matchMarketModel) {
        this.context = context;
        this.matchMarketModel = matchMarketModel;
        this.list.clear();
        if (matchMarketModel.getRunners() != null) {
            this.list.addAll(matchMarketModel.getRunners());
        }
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_dashboard_tennis_child));
    }

    public List<BetSlipModel> getPlacedBets(String marketId) {
        return null;
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(MatchMarketModel matchMarketModel, DiffUtil.DiffResult diffResult) {
        this.matchMarketModel = matchMarketModel;
        this.list.clear();
        if (matchMarketModel.getRunners() != null) {
            this.list.addAll(matchMarketModel.getRunners());
        }
        diffResult.dispatchUpdatesTo(this);
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tv_title;
        private TextView tv_status;
        private LinearLayout ll_back;
        private TextView tv_back_price;
        private TextView tv_back_size;
        private LinearLayout ll_lay;
        private TextView tv_lay_price;
        private TextView tv_lay_size;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_status = itemView.findViewById(R.id.tv_status);
            ll_back = itemView.findViewById(R.id.ll_back);
            tv_back_price = itemView.findViewById(R.id.tv_back_price);
            tv_back_size = itemView.findViewById(R.id.tv_back_size);
            ll_lay = itemView.findViewById(R.id.ll_lay);
            tv_lay_price = itemView.findViewById(R.id.tv_lay_price);
            tv_lay_size = itemView.findViewById(R.id.tv_lay_size);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;

            if (matchMarketModel.isMatchDisable()
                    || !matchMarketModel.isVisibility()) {
                ll_back.setOnClickListener(null);
                ll_lay.setOnClickListener(null);
                ll_back.setAlpha(0.5f);
                ll_lay.setAlpha(0.5f);
            } else {
                ll_back.setOnClickListener(this);
                ll_lay.setOnClickListener(this);
                ll_back.setAlpha(1.0f);
                ll_lay.setAlpha(1.0f);
            }

            ll_back.setTag(position);
            ll_lay.setTag(position);

            RunnersModel runnersModel = list.get(position);
            tv_title.setText(runnersModel.getRunnerName());
            List<AvailableToLayModel> lay = runnersModel.getLay();
            if (lay != null && lay.size() > 0) {
                AvailableToLayModel availableToLayModel = lay.get(0);
                tv_lay_price.setText(availableToLayModel.getPriceText());
                tv_lay_size.setText(availableToLayModel.getSizeText());
            } else {
                tv_lay_price.setText("--");
                tv_lay_size.setText("-");
            }
            List<AvailableToBackModel> back = runnersModel.getBack();
            if (back != null && back.size() > 0) {
                AvailableToBackModel availableToLayModel = back.get(0);
                tv_back_price.setText(availableToLayModel.getPriceText());
                tv_back_size.setText(availableToLayModel.getSizeText());
            } else {
                tv_back_price.setText("--");
                tv_back_size.setText("-");
            }

            double data = runnersModel.getWinLossValue();
            List<BetSlipModel> placedBets = getPlacedBets(matchMarketModel.getMarketid());
            if (placedBets != null && placedBets.size() > 0) {
                for (BetSlipModel betSlipModel : placedBets) {
                    if (runnersModel.getSelectionId().equals(betSlipModel.getSelectionId())) {
                        double PL = 0.0;
                        if (betSlipModel.isValidString(betSlipModel.getCurrentPL())) {
                            try {
                                PL = Double.parseDouble(betSlipModel.getCurrentPL());
                            } catch (NumberFormatException ignore) {

                            }
                        }
                        if (betSlipModel.isBack()) {
                            data += PL;
                        } else {
                            data -= PL;
                        }
                    } else {
                        double STACKAMOUNT = 0.0;
                        if (betSlipModel.isValidString(betSlipModel.getCurrentStack())) {
                            try {
                                STACKAMOUNT = Double.parseDouble(betSlipModel.getCurrentStack());
                            } catch (NumberFormatException ignore) {

                            }
                        }
                        if (betSlipModel.isBack()) {
                            data -= STACKAMOUNT;
                        } else {
                            data += STACKAMOUNT;
                        }
                    }
                }
            }
            tv_status.setText(runnersModel.getValidCurrencyFormat(data));

            if (data < 0) {
                tv_status.setTextColor(getContext().getResources().getColor(R.color.color_red));
            } else {
                tv_status.setTextColor(getContext().getResources().getColor(R.color.color_green));
            }
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }
    }
}
