package com.lotus.utilites;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.MatchMarketModel;

import java.util.List;

public class DiffCallBackMatchMarketModel extends DiffUtil.Callback {
    private final List<MatchMarketModel> mOldList;
    private final List<MatchMarketModel> mNewList;

    public DiffCallBackMatchMarketModel(List<MatchMarketModel> mOldList, List<MatchMarketModel> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
    }

    @Override
    public int getOldListSize() {
        return this.mOldList == null ? 0 : this.mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return this.mNewList == null ? 0 : this.mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        MatchMarketModel oldData = this.mOldList.get(oldItemPosition);
        MatchMarketModel newData = this.mNewList.get(newItemPosition);
        return oldData.equals(newData);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MatchMarketModel oldData = this.mOldList.get(oldItemPosition);
        MatchMarketModel newData = this.mNewList.get(newItemPosition);
        return oldData.checkContentSame(newData);
    }
}
