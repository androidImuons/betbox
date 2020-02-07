package com.lotus.ui.main.dashboard.adapter;

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
import com.lotus.ui.main.MainActivity;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class SoccerDashAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<MatchDetailModel> list = new ArrayList<>();

    public SoccerDashAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_dash_board_soccer));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public List<BetSlipModel> getPlacedBets(String marketId) {
        return null;
    }

    public void update(List<MatchDetailModel> list, DiffUtil.DiffResult diffResult) {
        this.list.clear();
        if (list != null)
            this.list.addAll(list);
        diffResult.dispatchUpdatesTo(this);
    }

    public void updateRunners(List<MatchDetailModel> list) {
        if (getRecyclerView() == null) return;
        for (MatchDetailModel matchMarketModel : list) {
            int index = this.list.indexOf(matchMarketModel);
            if (index >= 0) {
                this.list.get(index).getMarkets().get(0).setRunners(matchMarketModel.getMarkets().get(0).getRunners());
                ViewHolder viewHolderForAdapterPosition = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(index);
                if (viewHolderForAdapterPosition != null) {
                    viewHolderForAdapterPosition.updateRunners(this.list.get(index).getMarkets().get(0),
                            matchMarketModel.getMarkets().get(0).getDiffResultRunners());
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

        private LinearLayout ll_header;
        private ImageView iv_star_icon;
        private ImageView iv_match_icon;
        private TextView tv_match_name;
        private TextView tv_match_date;
        private ImageView iv_play_icon;
        private RecyclerView recycler_view;
        private SoccerChildAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_header = itemView.findViewById(R.id.ll_header);
            iv_star_icon = itemView.findViewById(R.id.iv_star_icon);
            iv_match_icon = itemView.findViewById(R.id.iv_match_icon);
            tv_match_name = itemView.findViewById(R.id.tv_match_name);
            tv_match_date = itemView.findViewById(R.id.tv_match_date);
            iv_play_icon = itemView.findViewById(R.id.iv_play_icon);
            recycler_view = itemView.findViewById(R.id.recycler_view);
            ((MainActivity) context).updateViewVisibility(recycler_view, View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);
        }

        public void updateRunners(MatchMarketModel matchMarketModel, DiffUtil.DiffResult diffResult) {
            if (adapter != null) {
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
            tv_match_date.setText(matchDetailModel.getFormattedMatchdate());
            recycler_view.setTag(position);
            iv_star_icon.setTag(position);
            iv_star_icon.setOnClickListener(this);
            adapter = new SoccerChildAdapter(getContext(), matchMarketModel) {
                @Override
                public List<BetSlipModel> getPlacedBets(String marketId) {
                    return SoccerDashAdapter.this.getPlacedBets(marketId);
                }
            };
            recycler_view.setAdapter(adapter);
            ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    performChildItemClick((Integer) recycler_view.getTag(), position, v);
                }
            });
            if (matchMarketModel.isInPlay() && !matchMarketModel.isSuspend()) {
                ((MainActivity) context).updateViewVisibility(tv_match_date, View.GONE);
                ((MainActivity) context).updateViewVisibility(recycler_view, View.VISIBLE);
                ((MainActivity) context).updateViewVisibility(iv_play_icon, View.VISIBLE);
            } else {
                ((MainActivity) context).updateViewVisibility(recycler_view, View.GONE);
                ((MainActivity) context).updateViewVisibility(tv_match_date, View.VISIBLE);
                ((MainActivity) context).updateViewVisibility(iv_play_icon, View.GONE);
            }

//            if (MyApplication.getInstance().favouriteMarketIds.contains(matchMarketModel.getMarketid())) {
            if (matchMarketModel.isFavourite()) {
                iv_star_icon.setImageResource(R.mipmap.star_color_one);
            } else {
                iv_star_icon.setImageResource(R.mipmap.star_color_two);
            }

        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }
    }


}
