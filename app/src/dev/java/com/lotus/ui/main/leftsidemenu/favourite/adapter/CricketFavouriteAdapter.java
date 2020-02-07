package com.lotus.ui.main.leftsidemenu.favourite.adapter;

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

public class CricketFavouriteAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<MatchDetailModel> list = new ArrayList<>();


    public CricketFavouriteAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_favourite_cricket));
    }

    public List<BetSlipModel> getPlacedBets(String marketId) {
        return null;
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(List<MatchDetailModel> list, DiffUtil.DiffResult diffResult) {
        this.list.clear();
        if (list != null)
            this.list.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateRunners(List<MatchDetailModel> list) {
        if (getRecyclerView() == null) return;
        for (MatchDetailModel matchDetailModel : list) {
            for (int i = 0; i < this.list.size(); i++) {
                MatchDetailModel detailModel = this.list.get(i);
                if (detailModel.getUniqueIdForFav().equals(matchDetailModel.getUniqueIdForFav())) {
                    if(!detailModel.getMarkets().get(0).getStatus().equals(matchDetailModel.getMarkets().get(0).getStatus())){
                        notifyItemChanged(i);
                    }else{
                        detailModel.getMarkets().get(0).setRunners(matchDetailModel.getMarkets().get(0).getRunners());
                        ViewHolder viewHolderForAdapterPosition = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(i);
                        if (viewHolderForAdapterPosition != null) {
                            viewHolderForAdapterPosition.updateRunners(detailModel.getMarkets().get(0),
                                    matchDetailModel.getMarkets().get(0).getDiffResultRunners());
                        }
                    }

                    break;
                }
            }
        }


    }

    public void updateMatchRunner(BetSlipModel betSlipModel) {
        if (getItemCount() > 0) {
            for (int i = 0; i < getList().size(); i++) {
                MatchDetailModel matchMarketModel = getList().get(i);
                if (matchMarketModel.getMarkets().get(0).getMarketid().equals(betSlipModel.getMarketid())) {
                    ViewHolder viewHolderForAdapterPosition = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(i);
                    if (viewHolderForAdapterPosition != null) {
                        viewHolderForAdapterPosition.adapter.notifyDataSetChanged();
                    }
                    break;
                }
            }

        }
    }

    public MatchDetailModel getItem(int pos) {
        if (list == null || list.size() == 0) return null;
        return list.get(pos);
    }

    public List<MatchDetailModel> getList() {
        return list;
    }

    private class ViewHolder extends BaseViewHolder {

        private ImageView iv_star_icon;
        private LinearLayout ll_market_suspended;
        private TextView tv_match_name;
        private TextView tv_inplay;
        private TextView tv_item_name;
        private RecyclerView recycler_view;
        private CricketFavouriteChildAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_market_suspended = itemView.findViewById(R.id.ll_market_suspended);
            iv_star_icon = itemView.findViewById(R.id.iv_star_icon);
            tv_match_name = itemView.findViewById(R.id.tv_match_name);
            tv_inplay = itemView.findViewById(R.id.tv_inplay);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            recycler_view = itemView.findViewById(R.id.recycler_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
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
            MatchDetailModel matchDetailModel = list.get(position);
            MatchMarketModel matchMarketModel = matchDetailModel.getMarkets().get(0);
            String matchName = matchDetailModel.getMatchName();
            tv_match_name.setText(matchName);
            if (matchMarketModel.isInPlay()) {
                tv_inplay.setText("In-Play");
            } else {
                tv_inplay.setText("Going In-Play");
            }
            tv_item_name.setText(matchMarketModel.getMarket_name());
            recycler_view.setTag(position);
            iv_star_icon.setTag(position);
            iv_star_icon.setOnClickListener(this);

            adapter = new CricketFavouriteChildAdapter(getContext(), matchMarketModel) {
                @Override
                public List<BetSlipModel> getPlacedBets(String marketId) {
                    return CricketFavouriteAdapter.this.getPlacedBets(marketId);
                }
            };
            recycler_view.setAdapter(adapter);
            ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    performChildItemClick((Integer) recycler_view.getTag(), position, v);
                }
            });

//            if (MyApplication.getInstance().favouriteMarketIds.contains(matchMarketModel.getMarketid())) {
            if (matchMarketModel.isFavourite()) {
                iv_star_icon.setImageResource(R.mipmap.star_color_one);
            } else {
                iv_star_icon.setImageResource(R.mipmap.star_color_two);
            }

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
