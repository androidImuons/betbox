package com.lotus.ui.main.leftsidemenu.competitionDetail.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.CompetitionDetailModel;
import com.utilities.ItemClickSupport;

import java.util.List;

public class CompetitionDetailAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<CompetitionDetailModel> list;

    public CompetitionDetailAdapter(Context context, List<CompetitionDetailModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_competitions_detail));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }


    private class ViewHolder extends BaseViewHolder {
        private TextView tv_date;
        private RecyclerView recycler_view;
        private CompetitionChildAdapter adapter;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            recycler_view = itemView.findViewById(R.id.recycler_view);
//            ((MainActivity) context).updateViewVisibility(recycler_view, View.GONE);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recycler_view.setLayoutManager(layoutManager);

        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            CompetitionDetailModel detailModel = list.get(position);
            tv_date.setText(detailModel.getDate());
            adapter = new CompetitionChildAdapter(getContext());
            adapter.updateData(detailModel.getMatch());
            recycler_view.setAdapter(adapter);
            recycler_view.setTag(position);
            ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    performChildItemClick((Integer) recycler_view.getTag(), position, v);
                }
            });
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }
    }
}
