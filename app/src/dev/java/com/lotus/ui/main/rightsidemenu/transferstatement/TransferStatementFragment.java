package com.lotus.ui.main.rightsidemenu.transferstatement;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseActivity;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.LedgerModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.TransferRequestModel;
import com.lotus.model.ui_model.TypeModel;
import com.lotus.model.web_response.LedgerResponseModel;
import com.lotus.ui.main.dialog.datadialog.DataDialog;
import com.lotus.ui.main.rightsidemenu.transferstatement.adapter.LedgerAdapter;
import com.medy.retrofitwrapper.WebRequest;
import com.utilities.DatePickerUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransferStatementFragment extends AppBaseFragment {

    private TextView tv_type;
    private TextView tv_from_date;
    private TextView tv_to_date;
    private LinearLayout ll_to_date;
    private LinearLayout ll_from_date;
    private LinearLayout ll_search;
    private TextView tv_reset;
    private RecyclerView recycler_view;
    private List<TypeModel> typeList;

    private List<LedgerModel> ledgerList;
    private LedgerAdapter ledgerAdapter;

    private TextView tv_cp_credit;
    private TextView tv_cp_debit;
    private TextView tv_no_record_found;
    private LinearLayout ll_view;


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_transfer_statement;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        tv_type = getView().findViewById(R.id.tv_type);
        tv_from_date = getView().findViewById(R.id.tv_from_date);
        tv_to_date = getView().findViewById(R.id.tv_to_date);
        ll_to_date = getView().findViewById(R.id.ll_to_date);
        ll_from_date = getView().findViewById(R.id.ll_from_date);
        ll_search = getView().findViewById(R.id.ll_search);
        tv_reset = getView().findViewById(R.id.tv_reset);
        recycler_view = getView().findViewById(R.id.recycler_view);
        tv_cp_credit = getView().findViewById(R.id.tv_cp_credit);
        tv_cp_debit = getView().findViewById(R.id.tv_cp_debit);
        tv_no_record_found = getView().findViewById(R.id.tv_no_record_found);
        ll_view = getView().findViewById(R.id.ll_view);

        tv_type.setOnClickListener(this);
        ll_to_date.setOnClickListener(this);
        ll_from_date.setOnClickListener(this);
        ll_search.setOnClickListener(this);
        tv_reset.setOnClickListener(this);
        updateViewVisibility(tv_no_record_found, View.GONE);

        typeList = new ArrayList<>();
        ledgerList = new ArrayList<>();

        initializeUi();
    }

    private void initializeUi() {
        prepareTypeList();
        recycler_view.setLayoutManager(getVerticalLinearLayoutManager());

        initializeRecyclerView();
        getHistoryFromServer();
    }


    private void prepareTypeList() {
        TypeModel typeModel1 = new TypeModel();
        typeModel1.setId(1);
        typeModel1.setName("Ledger");
        typeList.add(typeModel1);

        TypeModel typeModel2 = new TypeModel();
        typeModel2.setId(2);
        typeModel2.setName("Commission");
        typeList.add(typeModel2);

        TypeModel typeModel3 = new TypeModel();
        typeModel3.setId(3);
        typeModel3.setName("Settlements");
        typeList.add(typeModel3);

        TypeModel typeModel4 = new TypeModel();
        typeModel4.setId(4);
        typeModel4.setName("Credit Limit");
        typeList.add(typeModel4);
        tv_type.setTag(typeList.get(0));
        tv_type.setText(typeList.get(0).getName());
    }

    private int getCurrentType() {
        int type = 1;
        if (tv_type.getTag() != null) {
            TypeModel typeModel = (TypeModel) tv_type.getTag();
            type = typeModel.getId();
        }

        return type;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_type:
                showDataDialog(getString(R.string.type), typeList, tv_type);
                break;
            case R.id.ll_from_date:
                showDatePickerDialog(tv_from_date);
                break;
            case R.id.ll_to_date:
                showDatePickerDialog(tv_to_date);
                break;
            case R.id.ll_search:
                getHistoryFromServer();
                break;
            case R.id.tv_reset:
                tv_from_date.setText("");
                tv_to_date.setText("");
                tv_from_date.setTag(null);
                tv_to_date.setTag(null);
                break;
        }
    }

    private void getHistoryFromServer() {
        try {
            UserModel userModel = getMyApplication().getUserModel();
            if (!isValidObject(userModel)) return;

            int type = getCurrentType();
            String from_date = "";
            String to_date = "";

            if (tv_from_date.getTag() != null) {
                Calendar calendar = (Calendar) tv_from_date.getTag();
                if (getValidDate(calendar) != null)
                    from_date = getValidDate(calendar);

            }
            if (tv_to_date.getTag() != null) {
                Calendar calendar = (Calendar) tv_to_date.getTag();
                if (getValidDate(calendar) != null)
                    to_date = getValidDate(calendar);

            }

            TransferRequestModel requestModel = new TransferRequestModel();

            requestModel.type = type;
            requestModel.from_date = from_date;
            requestModel.to_date = to_date;

            requestModel.user_type = userModel.getType();
            requestModel.user_id = userModel.getUser_id();
            getWebRequestHelper().chip_leger(requestModel, this);
            displayProgressBar(false);

        } catch (IllegalAccessException e) {
            dismissProgressBar();
        }
    }

    private void initializeRecyclerView() {

        ledgerAdapter = new LedgerAdapter(ledgerList);
        recycler_view.setAdapter(ledgerAdapter);

    }


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {
        super.onWebRequestResponse(webRequest);
        dismissProgressBar();
        if (webRequest == null) return;
        if (webRequest.getWebRequestId() == ID_CHIP_LEGER) {
            LedgerResponseModel responsePojo = webRequest.getResponsePojo(LedgerResponseModel.class);
            handleChipLedgerResponse(responsePojo);
        }
    }

    private void handleChipLedgerResponse(LedgerResponseModel responseModel) {
        if (!isValidObject(responseModel)) return;
        ledgerList.clear();
        updateAmountUi(responseModel);
        List<LedgerModel> list = responseModel.getChips_lgr();
        if (isValidObject(list) && list.size() > 0) {
            ledgerList.addAll(list);
            showNoRecordFoundUi(View.GONE);
        } else {
            showNoRecordFoundUi(View.VISIBLE);
//            showCustomToast("No Record Found");
        }
        ledgerAdapter.notifyDataSetChanged();
    }

    private void updateAmountUi(LedgerResponseModel model) {
        double cp_credit = model.getCurrentPageTotalCredit();
        int cp_color_credit = model.getAmountTextColor(cp_credit);

        double cp_debit = model.getCurrentPageTotalDebit();
        int cp_color_debit = model.getAmountTextColor(cp_debit);

        tv_cp_credit.setText("" + cp_credit);
        tv_cp_credit.setTextColor(getResources().getColor(cp_color_credit));

        tv_cp_debit.setText("" + cp_debit);
        //tv_cp_debit.setTextColor(getResources().getColor(cp_color_debit));

    }

    private void showNoRecordFoundUi(int visibility) {
        if (visibility == View.VISIBLE) {
//            showCustomToast("No Record Found");
            updateViewVisibility(ll_view, View.GONE);
        } else {
            updateViewVisibility(ll_view, View.VISIBLE);
        }
        updateViewVisibility(tv_no_record_found, visibility);

    }

    private void showDatePickerDialog(final TextView textView) {
        DatePickerUtil.showDatePicker(getContext(), textView, false,
                new DatePickerUtil.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(Calendar calendar) {
                        Date date = calendar.getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AppBaseActivity.APP_DATE_FORMAT);
                        String sel_date = simpleDateFormat.format(date);
                        textView.setTag(calendar);
                        textView.setText(sel_date);
                    }
                });
    }

    private void showDataDialog(String title, final List list, final TextView textView) {
        final DataDialog dialog = new DataDialog();
        dialog.setTitle(title);
        dialog.setList(list);
        dialog.setOnItemSelectedListeners(new DataDialog.OnItemSelectedListener() {
            @Override
            public void onItemSelectedListener(int position) {
                textView.setText(list.get(position).toString());
                textView.setTag(list.get(position));
                switch (textView.getId()) {

                }
                dialog.dismiss();

            }
        });

        dialog.show(getFragmentManager(), DataDialog.class.getSimpleName());
    }
}
