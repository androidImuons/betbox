package com.lotus.utilites;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.BetDataModel;

import java.util.List;

public class DiffCallBackUnMatchedBetModel extends DiffUtil.Callback {
    private final List<BetDataModel.BetUserData> mOldList;
    private final List<BetDataModel.BetUserData> mNewList;

    public DiffCallBackUnMatchedBetModel(List<BetDataModel.BetUserData> mOldList, List<BetDataModel.BetUserData> mNewList) {
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
        BetDataModel.BetUserData oldData = this.mOldList.get(oldItemPosition);
        BetDataModel.BetUserData newData = this.mNewList.get(newItemPosition);
        return oldData.equals(newData);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        BetDataModel.BetUserData oldData = this.mOldList.get(oldItemPosition);
        BetDataModel.BetUserData newData = this.mNewList.get(newItemPosition);
        return oldData.checkContentSame(newData);
    }
}
