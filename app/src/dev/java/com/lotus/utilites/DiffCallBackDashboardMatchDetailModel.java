package com.lotus.utilites;


import androidx.recyclerview.widget.DiffUtil;

import com.lotus.model.MatchDetailModel;

import java.util.List;

public class DiffCallBackDashboardMatchDetailModel extends DiffUtil.Callback {
    private final List<MatchDetailModel> mOldList;
    private final List<MatchDetailModel> mNewList;

    int tag = -1;

    public DiffCallBackDashboardMatchDetailModel(int tag, List<MatchDetailModel> mOldList, List<MatchDetailModel> mNewList) {
        this.mOldList = mOldList;
        this.mNewList = mNewList;
        this.tag = tag;
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
        try {
            MatchDetailModel oldData = this.mOldList.get(oldItemPosition);
            MatchDetailModel newData = this.mNewList.get(newItemPosition);
            return oldData.equals(newData);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        try {
            MatchDetailModel oldData = this.mOldList.get(oldItemPosition);
            MatchDetailModel newData = this.mNewList.get(newItemPosition);
            if (tag == 1) {
                return oldData.checkContentSame(newData);
            } else if (tag == 2) {
                return oldData.checkContentSame2(newData);
            }
        } catch (Exception e) {
            return false;
        }
        return true;

    }
}
