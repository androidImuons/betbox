package com.lotus.ui.main.dialog.search.autofilte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lotus.R;
import com.lotus.model.SearchSuggestionModel;
import com.lotus.model.web_response.SearchSuggestionResponseModel;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manish Kumar
 * @since 29/9/17
 */


/**
 * AutoCompleteAdapter
 * <p>
 * This is use for {@link AutoCompleteTextView}
 * in application this is use for show search suggestion
 */
public class AutoCompleteAdapter extends android.widget.BaseAdapter implements
        Filterable, AutoCompleteHelper.ResultAvailableListener {

    LayoutInflater layoutInflater;
    List<SearchSuggestionModel> mResultList = new ArrayList<>();
    SearchAutoComplete searchAutoComplete;
    Context context;

    public AutoCompleteAdapter (Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        searchAutoComplete = new SearchAutoComplete(context);
        searchAutoComplete.setResultAvailableListener(this);
    }

    @Override
    public int getCount () {
        return mResultList == null ? 0 : mResultList.size();
    }

    @Override
    public SearchSuggestionModel getItem (int position) {
        return mResultList == null || mResultList.size() - 1 < position ?
                null : mResultList.get(position);
    }

    @Override
    public long getItemId (int position) {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(position);

        return convertView;
    }

    @Override
    public Filter getFilter () {
        return searchAutoComplete.getFilter();
    }

    @Override
    public void onResultAvailable (WebServiceBaseResponseModel baseWebServiceModelResponse) {
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

    public class ViewHolder {
        View itemView;
        TextView text1;


        public ViewHolder (View itemView) {
            this.itemView = itemView;
            text1 = (TextView) itemView.findViewById(android.R.id.text1);
        }

        public void setData (int position) {
            SearchSuggestionModel searchSuggestionModel = getItem(position);
            if (searchSuggestionModel == null) return;
            text1.setTextColor(context.getResources().getColor(R.color.color_black));
            text1.setText(searchSuggestionModel.toString());
        }

    }
}