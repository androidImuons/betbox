package com.lotus.ui.main.dialog.bethistorydialog;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.customviews.TypefaceTextView;
import com.lotus.R;
import com.lotus.appBase.AppBaseDialogFragment;
import com.lotus.model.BetHistoryPLModel;
import com.lotus.model.web_response.BetHistoryPLResponseModel;
import com.lotus.ui.main.dialog.bethistorydialog.adapter.BetHistoryPLAdapter;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.ItemClickSupport;

import java.util.List;

public class BetHistoryPLDialog extends AppBaseDialogFragment {

    private RecyclerView recycler_view;
    private TypefaceTextView tv_title;
    private List<BetHistoryPLModel> dataList;

    public void setList(List<BetHistoryPLModel> list) {
        this.dataList = list;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_data;
    }

    @Override
    public int getFragmentContainerResourceId() {
        return -1;
    }

    @Override
    public void initializeComponent() {
        recycler_view = getView().findViewById(R.id.recycler_view);
        tv_title = getView().findViewById(R.id.tv_title);
        tv_title.setText("Bet History");

        if (dataList != null && dataList.size() > 0)
            initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(new BetHistoryPLAdapter(dataList));
        ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // onItemSelectedListeners.onItemSelectedListener(position);
                switch (v.getId()) {

                }

            }
        });
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater inflate = LayoutInflater.from(getActivity());
        View layout = inflate.inflate(getLayoutResourceId(), null);

        //set dialog_country view
        dialog.setContentView(layout);
        //setup dialog_country window param
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        // wlmp.gravity = Gravity.LEFT | Gravity.TOP;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setTitle(null);
        //  setCancelable(true);
        //   dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        super.onWebRequestResponse(webRequest);
        dismissProgressBar();

        if (webRequest != null) {
            if (webRequest.getWebRequestId() == ID_URL_BET_HISTORY_PL) {
                handleBetHistoryPLResponse(webRequest);
            }
        }
    }

    private void handleBetHistoryPLResponse(WebRequest webRequest) {
        BetHistoryPLResponseModel responsePojo = webRequest.getResponsePojo(BetHistoryPLResponseModel.class);
        if (responsePojo == null) return;
        List<BetHistoryPLModel> betList = responsePojo.getGetBetPl();
        if (betList != null && betList.size() > 0) {
            showBetHistoryDialog(betList);
        } else {
            showErrorMessage("No Bets available");
        }
    }

    private void showBetHistoryDialog(List<BetHistoryPLModel> betList) {

    }
}
