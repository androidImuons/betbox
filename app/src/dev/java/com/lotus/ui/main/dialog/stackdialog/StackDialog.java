package com.lotus.ui.main.dialog.stackdialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseDialogFragment;
import com.lotus.model.StackModel;
import com.lotus.model.request_model.MatchStackRequestModel;
import com.lotus.rest.WebRequestHelper;
import com.lotus.ui.main.dialog.stackdialog.adapter.StackAdapter;
import com.medy.retrofitwrapper.WebRequest;
import com.medy.retrofitwrapper.WebServiceBaseResponseModel;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

public class StackDialog extends AppBaseDialogFragment {

    private RecyclerView recycler_view;
    private TextView tv_title;
    private TextView tv_cancel;
    private TextView tv_save;
    private List<String> dataList;
    String title;

    private StackAdapter stackAdapter;

    public void setList(List<String> list) {
        this.dataList = list;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_stack;
    }

    @Override
    public int getFragmentContainerResourceId() {
        return -1;
    }

    @Override
    public void initializeComponent() {
        recycler_view = getView().findViewById(R.id.recycler_view);
        tv_title = getView().findViewById(R.id.tv_title);
        tv_cancel = getView().findViewById(R.id.tv_cancel);
        tv_save = getView().findViewById(R.id.tv_save);

        tv_cancel.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        if (title != null)
            tv_title.setText(title);
        if (dataList != null && dataList.size() > 0)
            initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(new GridLayoutManager(getContext(), 2));
        stackAdapter = new StackAdapter(dataList);
        recycler_view.setAdapter(stackAdapter);
        ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
            }
        });
    }

    public List<String> getNewStackList() {
        boolean isError = false;
        List<String> newStackList = new ArrayList<>();
        for (int i = 0; i < stackAdapter.getDataCount(); i++) {
            StackAdapter.ViewHolder viewHolder = (StackAdapter.ViewHolder) recycler_view.
                    findViewHolderForAdapterPosition(i);
            String value = viewHolder.et_name.getText().toString().trim();
            if (isValidString(value)) {
                newStackList.add(value);
            } else {
                isError = true;
                break;
            }
        }

        if (isError) {
            showErrorMessage("Field can not be empty.");
            return null;
        }
        return newStackList;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_save:
                saveOnServer();
                break;
        }
    }

    private void saveOnServer() {

        List<String> newStackList = getNewStackList();
        if (newStackList == null || newStackList.size() == 0) {
            return;
        }
        MatchStackRequestModel requestModel = new MatchStackRequestModel();
        requestModel.match_stake = newStackList;
        displayProgressBar(false);
        getWebRequestHelper().stake_setting(requestModel, this);
    }

    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        super.onWebRequestResponse(webRequest);
        dismissProgressBar();
        if (webRequest.isSuccess()) {
            switch (webRequest.getWebRequestId()) {
                case WebRequestHelper.ID_STAKE_SETTING:
                    handleMatchStackResponse(webRequest);
                    break;
            }
        } else {
            String msg = webRequest.getErrorMessageFromResponse();
            if (msg != null && !msg.isEmpty()) {
                webRequest.showInvalidResponse(msg);
            }
        }
    }

    private void handleMatchStackResponse(WebRequest webRequest) {
        WebServiceBaseResponseModel responseModel = webRequest.getBaseResponsePojo();
        MatchStackRequestModel requestModel = webRequest.getExtraData(KEY_STAKE_SETTING);

        if (requestModel != null && responseModel != null) {
            if (!responseModel.isError()) {
                try {
                    StackModel userStack = getMyApplication().getUserStack();
                    userStack.setMatch_stake(requestModel.match_stake);
                    getMyApplication().updateUserStack(userStack);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                showCustomToast(responseModel.getMessage());
            }
        }
        dismiss();
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
        wlmp.gravity = Gravity.TOP;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;

        dialog.setTitle(null);
        setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }
}
