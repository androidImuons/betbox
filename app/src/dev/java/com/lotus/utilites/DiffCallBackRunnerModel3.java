package com.lotus.utilites;


import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.RunnersModel;

import java.util.List;

public class DiffCallBackRunnerModel3 extends DiffUtil.Callback {
    private final List<RunnersModel> mOldList;
    private final List<RunnersModel> mNewList;

    public DiffCallBackRunnerModel3(List<RunnersModel> mOldList, List<RunnersModel> mNewList) {
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
        RunnersModel oldData = this.mOldList.get(oldItemPosition);
        RunnersModel newData = this.mNewList.get(newItemPosition);
        return oldData.equals(newData);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        RunnersModel oldData = this.mOldList.get(oldItemPosition);
        RunnersModel newData = this.mNewList.get(newItemPosition);
        newData.setWinValue(oldData.getWinValue());
        newData.setLossValue(oldData.getLossValue());
        return oldData.checkContentSame(newData);
    }
}
