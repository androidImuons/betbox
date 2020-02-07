package com.lotus.ui.main.dialog.search;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseDialogFragment;
import com.lotus.model.MatchDetailModel;
import com.lotus.model.SearchSuggestionModel;
import com.lotus.ui.main.dialog.search.adapter.SearchAdapter;
import com.lotus.ui.main.matchDetail.MatchDetailFragment;
import com.utilities.ItemClickSupport;

/**
 * Created by Azher on 16/11/18.
 */
public class SearchDialog extends AppBaseDialogFragment {

    private ImageView iv_back;
    private EditText tv_search;
    private RecyclerView recycler_view;
    private SearchAdapter adapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_search;
    }

    @Override
    public int getFragmentContainerResourceId() {
        return -1;
    }

    @Override
    public void initializeComponent() {
        recycler_view = getView().findViewById(R.id.recycler_view);
        iv_back = getView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        tv_search = getView().findViewById(R.id.tv_search);
        tv_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s);
            }
        });
        initializeRecyclerView();
        tv_search.post(new Runnable() {
            @Override
            public void run() {
                try {
                    getMainActivity().showKeyboard(tv_search);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                dismiss();
                break;
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflate = LayoutInflater.from(getActivity());
        View layout = inflate.inflate(getLayoutResourceId(), null);
        final EditText tv_search = layout.findViewById(R.id.tv_search);
        //set dialog_country view
        dialog.setContentView(layout);
        //setup dialog_country window param
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.TOP;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setTitle(null);

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    getMainActivity().hideKeyboard(tv_search);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                try {
                    getMainActivity().hideKeyboard(tv_search);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        //  setCancelable(true);
        //   dialog.setCanceledOnTouchOutside(false);
    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SearchAdapter(getContext());
        recycler_view.setAdapter(adapter);
        ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                SearchSuggestionModel item = adapter.getItem(position);
                if (item == null) return;
                addMatchDetailFragment(item.getMatchListModel());
                dismiss();

            }
        });
    }

    private void addMatchDetailFragment(MatchDetailModel matchDetailModel) {
        if (matchDetailModel == null) return;
        MatchDetailFragment fragment = new MatchDetailFragment();
        fragment.setMatchDetailModel(matchDetailModel);
        int enterAnimation = R.anim.right_in;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.left_out;
        try {
            getMainActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
