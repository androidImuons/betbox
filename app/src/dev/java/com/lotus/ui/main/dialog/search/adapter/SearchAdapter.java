package com.lotus.ui.main.dialog.search.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.SearchSuggestionModel;
import com.lotus.model.web_response.SearchSuggestionResponseModel;
import com.lotus.ui.main.dialog.search.autofilte.AutoCompleteHelper;
import com.lotus.ui.main.dialog.search.autofilte.SearchAutoComplete;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azher on 16/11/18.
 */
public class SearchAdapter extends BaseRecycleAdapter implements
        Filterable, AutoCompleteHelper.ResultAvailableListener {

    private Context context;
    List<SearchSuggestionModel> mResultList = new ArrayList<>();
    SearchAutoComplete searchAutoComplete;

    public SearchAdapter(Context context) {
        this.context = context;
        searchAutoComplete = new SearchAutoComplete(context);
        searchAutoComplete.setResultAvailableListener(this);
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_search));
    }

    @Override
    public int getDataCount() {
        return mResultList == null ? 0 : mResultList.size();
    }

    public SearchSuggestionModel getItem(int position) {
        return mResultList == null || mResultList.size() - 1 < position ?
                null : mResultList.get(position);
    }

    @Override
    public Filter getFilter() {
        return searchAutoComplete.getFilter();
    }

    @Override
    public void onResultAvailable(WebServiceBaseResponseModel baseWebServiceModelResponse) {
        mResultList.clear();
        if (baseWebServiceModelResponse != null) {
            SearchSuggestionResponseModel searchSuggestionResponseModel = (SearchSuggestionResponseModel) baseWebServiceModelResponse;
            List<SearchSuggestionModel> list = searchSuggestionResponseModel.getData();
            if (list != null) {
                mResultList.addAll(list);
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder extends BaseViewHolder {

        private TextView tv_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }

        @Override
        public void setData(int position) {
            if (mResultList == null) return;
            SearchSuggestionModel searchSuggestionModel = mResultList.get(position);
            tv_name.setText(searchSuggestionModel.getMatchName());
            if (position % 2 == 0)
                tv_name.setBackgroundColor(context.getResources().getColor(R.color.color_white));
            else
                tv_name.setBackgroundColor(context.getResources().getColor(R.color.colorGray30));
        }
    }
}
