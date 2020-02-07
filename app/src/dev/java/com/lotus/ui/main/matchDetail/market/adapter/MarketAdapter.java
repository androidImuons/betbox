package com.lotus.ui.main.matchDetail.market.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.BetSlipModel;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.MatchMarketModel;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class MarketAdapter extends BaseRecycleAdapter {


    private Context context;
    private List<MatchMarketModel> list = new ArrayList<>();
    MatchDetailModel matchDetailModel;

    public MarketAdapter(Context context) {
        this.context = context;
    }

    public List<BetSlipModel> getPlacedBets(String marketId) {
        return null;
    }


    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_match_market));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(MatchDetailModel matchDetailModel, DiffUtil.DiffResult diffResult) {
        synchronized (list) {
            this.matchDetailModel = matchDetailModel;

            this.list.clear();
            if (matchDetailModel.getMarkets() != null && matchDetailModel.getMarkets().size() > 0) {
                this.list.addAll(matchDetailModel.getMarkets());
            }

            diffResult.dispatchUpdatesTo(this);
        }
    }

    public void updateRunners(List<MatchMarketModel> list) {
        if (getRecyclerView() == null) return;
        synchronized (this.list) {
            for (MatchMarketModel matchMarketModel : list) {
                int index = this.list.indexOf(matchMarketModel);
                if (index >= 0) {
                    String status = this.list.get(index).getStatus();
                    this.list.get(index).setStatus(matchMarketModel.getStatus());
                    this.list.get(index).setInPlay(matchMarketModel.isInPlay());
                    this.list.get(index).setMatchDisable(matchMarketModel.isMatchDisable());
                    this.list.get(index).setRunners(matchMarketModel.getRunners());

                    if(!status.equals(matchMarketModel.getStatus())){
                        notifyItemChanged(index);
                    }else{
                        updateRunners(index, matchMarketModel);
                    }

                }
            }
        }

    }

    public void updateRunners(int index, MatchMarketModel matchMarketModel) {
        ViewHolder viewHolderForAdapterPosition = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(index);
        if (viewHolderForAdapterPosition != null) {
            viewHolderForAdapterPosition.updateRunners(this.list.get(index),
                    matchMarketModel.getDiffResultRunners());
        }
    }

    public void updateMatchRunner(BetSlipModel betSlipModel) {
        if (getItemCount() > 0) {
            for (int i = 0; i < getList().size(); i++) {
                MatchMarketModel matchMarketModel = getList().get(i);
                if (matchMarketModel.getMarketid().equals(betSlipModel.getMarketid())) {
                    ViewHolder viewHolderForAdapterPosition = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(i);
                    if (viewHolderForAdapterPosition != null) {
                        viewHolderForAdapterPosition.adapter.notifyDataSetChanged();
                    }
                    break;
                }
            }

        }
    }

    public MatchMarketModel getItem(int pos) {
        if (list == null || list.size() == 0) return null;
        return list.get(pos);
    }

    public List<MatchMarketModel> getList() {
        return list;
    }

    public void notifyItemFavourite(int i) {
        this.notifyItemChanged(i);
    }


    private class ViewHolder extends BaseViewHolder {

        private ImageView iv_star_icon;
        private TextView tv_item_name;
        private LinearLayout ll_market_suspended;
        private RecyclerView recycler_view;
        private MarketChildAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_market_suspended = itemView.findViewById(R.id.ll_market_suspended);
            iv_star_icon = itemView.findViewById(R.id.iv_star_icon);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            recycler_view = itemView.findViewById(R.id.recycler_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
        }

        public void updateRunners(MatchMarketModel matchMarketModel, DiffUtil.DiffResult diffResult) {
            if (adapter != null && diffResult != null) {
                adapter.update(matchMarketModel, diffResult);
            }
        }


        @Override
        public void setData(int position) {
            if (list == null) return;
            MatchMarketModel matchMarketModel = list.get(position);
            if (matchMarketModel.isValidString(matchMarketModel.getMarket_name())) {
                tv_item_name.setText(matchMarketModel.getMarket_name());
            } else {
                tv_item_name.setText("Match Odds");
            }
            recycler_view.setTag(position);
            iv_star_icon.setTag(position);
            iv_star_icon.setOnClickListener(this);
            adapter = new MarketChildAdapter(getContext(), matchMarketModel) {
                @Override
                public List<BetSlipModel> getPlacedBets(String marketId) {
                    return MarketAdapter.this.getPlacedBets(marketId);
                }
            };
            recycler_view.setAdapter(adapter);
            ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    performChildItemClick((Integer) recycler_view.getTag(), position, v);
                }
            });

            // if (MyApplication.getInstance().favouriteMarketIds.contains(matchMarketModel.getMarketid())) {
            if (matchMarketModel.isFavourite()) {
                iv_star_icon.setImageResource(R.mipmap.star_color_one);
            } else {
                iv_star_icon.setImageResource(R.mipmap.star_color_two);
            }
            //  updateRunners(matchMarketModel, matchMarketModel.getDiffResultRunners());

            if(matchMarketModel.isSuspend()){
                if(ll_market_suspended.getVisibility()!=View.VISIBLE){
                    ll_market_suspended.setVisibility(View.VISIBLE);
                }
            }else{
                if(ll_market_suspended.getVisibility()!=View.GONE){
                    ll_market_suspended.setVisibility(View.GONE);
                }
            }

        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }

    }
}
