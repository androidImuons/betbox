package com.lotus.utilites;

import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.IndianSessionModel;

import java.util.List;

public class DiffCallBackIndianSessionModel2 extends DiffUtil.Callback {
    private final List<IndianSessionModel> mOldList;
    private final List<IndianSessionModel> mNewList;

    public DiffCallBackIndianSessionModel2(List<IndianSessionModel> mOldList, List<IndianSessionModel> mNewList) {
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
        IndianSessionModel oldData = this.mOldList.get(oldItemPosition);
        IndianSessionModel newData = this.mNewList.get(newItemPosition);
        return oldData.equals(newData);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        IndianSessionModel oldData = this.mOldList.get(oldItemPosition);
        IndianSessionModel newData = this.mNewList.get(newItemPosition);
        return oldData.checkContentSame2(newData);
    }
}
