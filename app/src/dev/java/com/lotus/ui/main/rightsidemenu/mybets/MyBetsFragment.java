package com.lotus.ui.main.rightsidemenu.mybets;


import android.app.Dialog;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.lotus.R;
import com.lotus.appBase.AppBaseActivity;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.model.AccountStatementModel;
import com.lotus.model.LoginHistoryModel;
import com.lotus.model.OnePageReportModel;
import com.lotus.model.PLByMatchIdModel;
import com.lotus.model.ProfitLossModel;
import com.lotus.model.UserModel;
import com.lotus.model.request_model.HistoryRequestModel;
import com.lotus.model.request_model.P_LByMatchIdRequestModel;
import com.lotus.model.ui_model.TypeModel;
import com.lotus.model.web_response.AccountStatementResponseModel;
import com.lotus.model.web_response.LoginHistoryResponseModel;
import com.lotus.model.web_response.OnePageReportResponseModel;
import com.lotus.model.web_response.PLByMatchIdResponseModel;
import com.lotus.model.web_response.ProfitLossResponseModel;
import com.lotus.ui.main.dialog.ExcelFileExportDialog;
import com.lotus.ui.main.dialog.datadialog.DataDialog;
import com.lotus.ui.main.dialog.plbymatchdialog.PLByMatchIdDialog;
import com.lotus.ui.main.rightsidemenu.mybets.adapter.AccountStatementAdapter;
import com.lotus.ui.main.rightsidemenu.mybets.adapter.LoginHistoryAdapter;
import com.lotus.ui.main.rightsidemenu.mybets.adapter.OnePageReportAdapter;
import com.lotus.ui.main.rightsidemenu.mybets.adapter.PagerAdapter;
import com.lotus.ui.main.rightsidemenu.mybets.adapter.ProfitLossAdapter;
import com.medy.retrofitwrapper.WebRequest;
import com.permissions.PermissionHelperNew;
import com.utilities.DatePickerUtil;
import com.utilities.ItemClickSupport;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyBetsFragment extends AppBaseFragment implements PermissionHelperNew.OnSpecificPermissionGranted {

    private boolean callOnResume = true;
    private TextView tv_type;
    private TextView tv_from_time;
    private TextView tv_to_time;
    private TextView tv_from_date;
    private TextView tv_to_date;
    private LinearLayout ll_to_date;
    private LinearLayout ll_from_date;
    private TextView tv_submit;
    private TextView tv_reset;
    private RecyclerView recycler_view;
    private TabLayout tab_layout;
    private TextView tv_no_record_found;

    private TextView tv_f_page;
    private TextView tv_p_page;
    private TextView tv_n_page;
    private TextView tv_l_page;
    private RecyclerView rv_pages;
    private RelativeLayout rl_view;

    private LinearLayout ll_add_view;
    private LinearLayout ll_crate_excel;

    private List<TypeModel> typeList;
    private List<String> timeList;
    private List<OnePageReportModel> betHistoryList;
    private List<ProfitLossModel> profitLossList;
    private List<AccountStatementModel> accountsList;
    private List<LoginHistoryModel> loginHistoryList;

    private OnePageReportAdapter betHistoryAdapter;
    private ProfitLossAdapter profitLossAdapter;
    private AccountStatementAdapter accountsAdapter;
    private LoginHistoryAdapter loginHistoryAdapter;

    private int type;
    private int currentPage = 1;
    private int totalPage = 1;
    private PagerAdapter pagerAdapter;

    public void setType(int type) {
        this.type = type;
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            TypeModel currentTypeModel = getCurrentTypeModel();

            switch (tab_layout.getSelectedTabPosition()) {
                case 0:
                    currentTypeModel.setSport_id(0);//all
                    break;
                case 1:
                    currentTypeModel.setSport_id(4);//cricket
                    break;
                case 2:
                    currentTypeModel.setSport_id(1);//soccer
                    break;
                case 3:
                    currentTypeModel.setSport_id(2);//tennis
                    break;
            }
            currentPage = 1;
            getHistoryFromServer(currentPage);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_my_bets;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        tv_type = getView().findViewById(R.id.tv_type);
        tv_from_time = getView().findViewById(R.id.tv_from_time);
        tv_to_time = getView().findViewById(R.id.tv_to_time);
        tv_from_date = getView().findViewById(R.id.tv_from_date);
        tv_to_date = getView().findViewById(R.id.tv_to_date);
        ll_to_date = getView().findViewById(R.id.ll_to_date);
        ll_from_date = getView().findViewById(R.id.ll_from_date);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_reset = getView().findViewById(R.id.tv_reset);
        recycler_view = getView().findViewById(R.id.recycler_view);
        tab_layout = getView().findViewById(R.id.tab_layout);
        tv_no_record_found = getView().findViewById(R.id.tv_no_record_found);
        tv_f_page = getView().findViewById(R.id.tv_f_page);
        tv_p_page = getView().findViewById(R.id.tv_p_page);
        tv_n_page = getView().findViewById(R.id.tv_n_page);
        tv_l_page = getView().findViewById(R.id.tv_l_page);
        rv_pages = getView().findViewById(R.id.rv_pages);
        rl_view = getView().findViewById(R.id.rl_view);
        ll_add_view = getView().findViewById(R.id.ll_add_view);
        ll_crate_excel = getView().findViewById(R.id.ll_crate_excel);
        ll_crate_excel.setOnClickListener(this);

        tv_type.setOnClickListener(this);
        tv_from_time.setOnClickListener(this);
        tv_to_time.setOnClickListener(this);
        ll_to_date.setOnClickListener(this);
        ll_from_date.setOnClickListener(this);
        tv_submit.setOnClickListener(this);
        tv_reset.setOnClickListener(this);

        tv_f_page.setOnClickListener(this);
        tv_p_page.setOnClickListener(this);
        tv_n_page.setOnClickListener(this);
        tv_l_page.setOnClickListener(this);

        tab_layout.addOnTabSelectedListener(onTabSelectedListener);

        updateViewVisibility(tv_no_record_found, View.GONE);

        typeList = new ArrayList<>();
        timeList = new ArrayList<>();
        betHistoryList = new ArrayList<>();
        profitLossList = new ArrayList<>();
        accountsList = new ArrayList<>();
        loginHistoryList = new ArrayList<>();


        initializeUi();
    }

    private void initializeTabLayout() {
        tab_layout.addTab(tab_layout.newTab().setText("All"));
        tab_layout.addTab(tab_layout.newTab().setText("Cricket"));
        tab_layout.addTab(tab_layout.newTab().setText("Soccer"));
        tab_layout.addTab(tab_layout.newTab().setText("Tennis"));
    }

    private void initializeUi() {
        prepareTypeList();
        initializeTabLayout();

        prepareTimeList();
        recycler_view.setLayoutManager(getVerticalLinearLayoutManager());

        initializeRecyclerView();
        initializePagerRecyclerView();

        currentPage = 1;
        getHistoryFromServer(currentPage);
    }

    private void initializePagerRecyclerView() {
        rv_pages.setLayoutManager(getHorizontalLayoutManager());
        pagerAdapter = new PagerAdapter();
        pagerAdapter.setTotalPages(currentPage, totalPage);
        rv_pages.setAdapter(pagerAdapter);

        ItemClickSupport.addTo(rv_pages).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (v.getId()) {
                    case R.id.tv_current_page:
                        int data = position + 1;
                        if (currentPage != data) {
                            currentPage = data;
                            getHistoryFromServer(currentPage);
                        }
                        break;
                }

            }
        });
    }

    private TypeModel getCurrentTypeModel() {
        TypeModel typeModel = null;
        if (tv_type.getTag() != null) {
            typeModel = (TypeModel) tv_type.getTag();
        }
        return typeModel;
    }

    private void initializeRecyclerView() {

        switch (getCurrentTypeModel().getId()) {
            case 1:
                betHistoryAdapter = new OnePageReportAdapter(betHistoryList);
                recycler_view.setAdapter(betHistoryAdapter);
                break;
            case 2:
                profitLossAdapter = new ProfitLossAdapter(profitLossList);
                recycler_view.setAdapter(profitLossAdapter);
                break;
            case 3:
                accountsAdapter = new AccountStatementAdapter(accountsList);
                recycler_view.setAdapter(accountsAdapter);
                break;
            case 4:
                loginHistoryAdapter = new LoginHistoryAdapter(loginHistoryList);
                recycler_view.setAdapter(loginHistoryAdapter);
                break;
        }

        ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (getCurrentTypeModel().getId()) {
                    case 1:
                        break;
                    case 2:
                        switch (v.getId()) {
                            case R.id.iv_add:
                                ProfitLossModel profitLossModel = profitLossList.get(position);
                                getPLByMatchFromServer(profitLossModel);
                                break;
                        }
                        break;
                    case 3:

                        break;
                    case 4:
                        break;
                }
            }
        });


    }

    private void getPLByMatchFromServer(ProfitLossModel profitLossModel) {
        UserModel userModel = null;
        try {
            userModel = getMyApplication().getUserModel();
            if (!isValidObject(userModel)) return;

            P_LByMatchIdRequestModel requestModel = new P_LByMatchIdRequestModel();
            requestModel.match_id = profitLossModel.getMatchId();
            requestModel.user_id = userModel.getUser_id();
            displayProgressBar(false);
            getWebRequestHelper().profitLossByMatchId(profitLossModel, requestModel, this);
        } catch (IllegalAccessException e) {
            dismissProgressBar();
        }

    }

    private void showProfitLossByMatchIdDialog(ProfitLossModel dataModel, List<PLByMatchIdModel> list) {
        PLByMatchIdDialog dialog = new PLByMatchIdDialog();
        dialog.setDataModel(dataModel);
        dialog.setList(list);
        dialog.show(getFragmentManager(), PLByMatchIdDialog.class.getSimpleName());

    }

    private void prepareTypeList() {

        TypeModel typeModel1 = new TypeModel();
        typeModel1.setId(1);
        typeModel1.setSport_id(0);
        typeModel1.setName("Bet History");
        typeList.add(typeModel1);

        TypeModel typeModel2 = new TypeModel();
        typeModel2.setId(2);
        typeModel2.setSport_id(0);
        typeModel2.setName("Profit & Loss");
        typeList.add(typeModel2);

        TypeModel typeModel3 = new TypeModel();
        typeModel3.setId(3);
        typeModel3.setSport_id(0);
        typeModel3.setName("Account Statements");
        typeList.add(typeModel3);

        TypeModel typeModel4 = new TypeModel();
        typeModel4.setId(4);
        typeModel4.setSport_id(0);
        typeModel4.setName("Login History");
        typeList.add(typeModel4);
        tv_type.setTag(typeList.get(type));
        tv_type.setText(typeList.get(type).getName());

        if (type == 1) {
            updateViewVisibility(tab_layout, View.VISIBLE);
        } else {
            updateViewVisibility(tab_layout, View.GONE);
        }
    }

    private void prepareTimeList() {
        int hh = 00;
        int mm = 00;
        String zeros = "00";
        String zero = "0";
        String time = zeros + ":" + zeros + ":" + zeros;
        timeList.add(time);

        for (int i = 1; i < 47; i++) {
            if (i % 2 != 0) {
                mm = 30;
            } else {
                hh = hh + 1;
                mm = 00;
            }

            String edit_time = (hh < 10 ? zero + hh : hh) + ":" +
                    (mm < 10 ? zero + mm : mm) + ":" + zeros;
            timeList.add(edit_time);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_type:
                showDataDialog(getString(R.string.type), typeList, tv_type);
                break;
            case R.id.tv_from_time:
                showDataDialog(getString(R.string.from_time), timeList, tv_from_time);
                break;
            case R.id.tv_to_time:
                showDataDialog(getString(R.string.to_time), timeList, tv_to_time);
                break;
            case R.id.ll_from_date:
                showDatePickerDialog(tv_from_date);
                break;
            case R.id.ll_to_date:
                showDatePickerDialog(tv_to_date);
                break;
            case R.id.tv_submit:
                currentPage = 1;
                getHistoryFromServer(currentPage);
                break;
            case R.id.tv_reset:
                tv_from_date.setText("");
                tv_to_date.setText("");
                tv_from_time.setText("");
                tv_to_time.setText("");
                break;
            case R.id.ll_crate_excel:
                goToForward();
                break;
            case R.id.tv_f_page:
                currentPage = 1;
                getHistoryFromServer(currentPage);
                break;
            case R.id.tv_p_page:
                if (currentPage > 1) {
                    currentPage = currentPage - 1;
                    getHistoryFromServer(currentPage);
                }
                break;
            case R.id.tv_n_page:
                if (currentPage < totalPage) {
                    currentPage = currentPage + 1;
                    getHistoryFromServer(currentPage);
                }
                break;
            case R.id.tv_l_page:
                currentPage = totalPage;
                getHistoryFromServer(currentPage);
                break;
        }
    }

    private void goToForward() {
        if (PermissionHelperNew.needStoragePermission(this, this)) return;
        createExcelSheet();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length > 0) {
            callOnResume = false;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelperNew.onSpecificRequestPermissionsResult(getActivity(), requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(boolean isGranted, boolean withNeverAsk, String permission, int requestCode) {
        if (requestCode == PermissionHelperNew.STORAGE_PERMISSION_REQUEST_CODE) {
            if (isGranted) {
                goToForward();
            } else {
                if (withNeverAsk) {
                    PermissionHelperNew.showNeverAskAlert(getActivity(), true, requestCode);
                } else {
                    PermissionHelperNew.showSpecificDenyAlert(getActivity(), permission, requestCode, false);
                }
            }
        }
    }

    private void createExcelSheet() {
        try {
            displayProgressBar(false);
            File reports = new File(Environment.getExternalStorageDirectory(), "/BetDipReports");
            if (!reports.exists()) {
                reports.mkdirs();
            }
            String csvFile = null;
            String fileName = tv_type.getText().toString().trim();
            if (fileName.equalsIgnoreCase("Bet History")) {
                csvFile = "BetHistoryReport";
            } else if (fileName.equalsIgnoreCase("Profit & Loss")) {
                csvFile = "ProfitAndLossReport";
            } else if (fileName.equalsIgnoreCase("Account Statements")) {
                csvFile = "AccountStatementReport";
            } else if (fileName.equalsIgnoreCase("Login History")) {
                csvFile = "LoginHistoryReport";
            }
           /* File[] files = reports.listFiles();
            int count = 0;
            for (int i = 0; i < files.length; i++) {
                if (files[i].getName().startsWith(csvFile)) {
                    count++;
                    csvFile = csvFile + "(" + count + ")";
                }
            }*/
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyy_hh_mm");
            String date = dateFormat.format(new Date(System.currentTimeMillis()));
            File file = new File(reports, csvFile + date + ".xls");
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));
            WritableWorkbook workbook;
            workbook = Workbook.createWorkbook(file, wbSettings);
            //Excel sheet name. 0 represents first sheet
            WritableSheet sheet = workbook.createSheet(csvFile, 0);
            if (fileName.equalsIgnoreCase("Bet History")) {
                if (betHistoryList != null && betHistoryList.size() > 0) {
                    sheet.addCell(new Label(0, 0, "Description"));
                    sheet.addCell(new Label(1, 0, "UserName"));
                    sheet.addCell(new Label(2, 0, "Selection Name"));
                    sheet.addCell(new Label(3, 0, "Type"));
                    sheet.addCell(new Label(4, 0, "Odds"));
                    sheet.addCell(new Label(5, 0, "Stack"));
                    sheet.addCell(new Label(6, 0, "Date"));
                    sheet.addCell(new Label(7, 0, "P|L"));
                    sheet.addCell(new Label(8, 0, "Profit"));
                    sheet.addCell(new Label(9, 0, "Liability"));
                    sheet.addCell(new Label(10, 0, "Status"));

                    for (int i = 0; i < betHistoryList.size(); i++) {
                        sheet.addCell(new Label(0, i + 1, betHistoryList.get(i).getDescription()));
                        sheet.addCell(new Label(1, i + 1, betHistoryList.get(i).getUserNm()));
                        sheet.addCell(new Label(2, i + 1, betHistoryList.get(i).getSelectionName()));
                        sheet.addCell(new Label(3, i + 1, betHistoryList.get(i).getType()));
                        sheet.addCell(new Label(4, i + 1, betHistoryList.get(i).getOdds()));
                        sheet.addCell(new Label(5, i + 1, betHistoryList.get(i).getStack()));
                        sheet.addCell(new Label(6, i + 1, betHistoryList.get(i).getMstDate()));
                        sheet.addCell(new Label(7, i + 1, betHistoryList.get(i).getP_L()));
                        sheet.addCell(new Label(8, i + 1, betHistoryList.get(i).getProfit()));
                        sheet.addCell(new Label(9, i + 1, betHistoryList.get(i).getLiability()));
                        sheet.addCell(new Label(10, i + 1, betHistoryList.get(i).getSTATUS()));
                    }
                    addExcelFileExportDialog(csvFile + date + ".xls\n" + "export successfully on this path\n" + file.getAbsolutePath());

                } else {
                    showErrorMessage("No record found");
                }

            } else if (fileName.equalsIgnoreCase("Profit & Loss")) {
                if (profitLossList != null && profitLossList.size() > 0) {
                    sheet.addCell(new Label(0, 0, "EventName"));
                    sheet.addCell(new Label(1, 0, "P_L"));
                    sheet.addCell(new Label(2, 0, "Comm"));
                    sheet.addCell(new Label(3, 0, "CreatedOn"));
                    for (int i = 0; i < profitLossList.size(); i++) {
                        sheet.addCell(new Label(0, i + 1, profitLossList.get(i).getEventName()));
                        sheet.addCell(new Label(1, i + 1, profitLossList.get(i).getPnL()));
                        sheet.addCell(new Label(2, i + 1, profitLossList.get(i).getComm()));
                        sheet.addCell(new Label(3, i + 1, profitLossList.get(i).getSettle_date()));
                    }
                    addExcelFileExportDialog(csvFile + date + ".xls\n" + "export successfully on this path\n" + file.getAbsolutePath());
                } else {
                    showErrorMessage("No record found");
                }


            } else if (fileName.equalsIgnoreCase("Account Statements")) {
                if (accountsList != null && accountsList.size() > 0) {
                    sheet.addCell(new Label(0, 0, "Date"));
                    sheet.addCell(new Label(1, 0, "UserName"));
                    sheet.addCell(new Label(2, 0, "Narration"));
                    sheet.addCell(new Label(3, 0, "Credit"));
                    sheet.addCell(new Label(4, 0, "Debit"));
                    sheet.addCell(new Label(5, 0, "Balance"));
                    printLog("accountsList " + accountsList.size());
                    UserModel userModel = getMyApplication().getUserModel();
                    for (int i = 0; i < accountsList.size(); i++) {
                        sheet.addCell(new Label(0, i + 1, accountsList.get(i).getSdate()));
                        sheet.addCell(new Label(1, i + 1, userModel.getUser_name()));
                        sheet.addCell(new Label(2, i + 1, accountsList.get(i).getNarration()));
                        sheet.addCell(new Label(3, i + 1, accountsList.get(i).getCredit()));
                        sheet.addCell(new Label(4, i + 1, accountsList.get(i).getDebit()));
                        sheet.addCell(new Label(5, i + 1, accountsList.get(i).getBalance()));
                    }
                    addExcelFileExportDialog(csvFile + date + ".xls\n" + "export successfully on this path\n" + file.getAbsolutePath());
                } else {
                    showErrorMessage("No record found");
                }

            } else if (fileName.equalsIgnoreCase("Login History")) {
                if (loginHistoryList != null && loginHistoryList.size() > 0) {
                    sheet.addCell(new Label(0, 0, "Date"));
                    sheet.addCell(new Label(1, 0, "Ip Address"));
                    sheet.addCell(new Label(2, 0, "Device Info"));
                    sheet.addCell(new Label(3, 0, "Browser Info"));
                    printLog("loginHistoryList " + loginHistoryList.size());
                    for (int i = 0; i < loginHistoryList.size(); i++) {
                        sheet.addCell(new Label(0, i + 1, loginHistoryList.get(i).getLogstdt()));
                        sheet.addCell(new Label(1, i + 1, loginHistoryList.get(i).getIpadress()));
                        sheet.addCell(new Label(2, i + 1, loginHistoryList.get(i).getDevice_info()));
                        sheet.addCell(new Label(3, i + 1, loginHistoryList.get(i).getBrowser_info()));
                    }
                    addExcelFileExportDialog(csvFile + date + ".xls\n" + "export successfully on this path\n" + file.getAbsolutePath());
                } else {
                    showErrorMessage("No record found");
                }

            }

            workbook.write();
            workbook.close();

            csvFile = null;
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        dismissProgressBar();
    }

    public void addExcelFileExportDialog(String filePath) {
        ExcelFileExportDialog dialog = new ExcelFileExportDialog(getContext());
        dialog.setFilePath(filePath);
        dialog.setConfirmBetDialogListener(new ExcelFileExportDialog.ConfirmBetDialogListener() {
            @Override
            public void onClickConfirm(Dialog dialog, View v) {
                switch (v.getId()) {
                    case R.id.tv_ok:
                        break;
                    case R.id.tv_cancel:
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if (callOnResume) {
            goToForward();
        } else {
            callOnResume = true;
        }*/
    }


    private void getHistoryFromServer(int currentPage) {
       /* if (currentPage < 1) {
            this.currentPage = 1;
            currentPage = 1;
        }*/
        initializeRecyclerView();
        try {
            UserModel userModel = getMyApplication().getUserModel();
            if (!isValidObject(userModel)) return;

            TypeModel type = getCurrentTypeModel();
            String from_date = "0";
            String to_date = "0";
            String from_time = "";
            String to_time = "";

            if (type == null) {
                showErrorMessage("Select Type");
                return;
            }

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
            if (tv_from_time.getTag() != null) {
                from_time = (String) tv_from_time.getTag();
            }

            if (tv_to_time.getTag() != null) {
                to_time = (String) tv_to_time.getTag();
            }
            HistoryRequestModel requestModel = new HistoryRequestModel();

            requestModel.type = type.getId();
            requestModel.sport_id = type.getSport_id();
            requestModel.from_date = from_date;
            requestModel.to_date = to_date;
            requestModel.from_time = from_time;
            requestModel.to_time = to_time;
            requestModel.page_no = currentPage;
            requestModel.user_id = userModel.getUser_id();
            displayProgressBar(false);
            getWebRequestHelper().one_page_report(requestModel, this);

        } catch (IllegalAccessException e) {
            dismissProgressBar();
        }
    }


    @Override
    public void onWebRequestResponse(WebRequest webRequest) {

        super.onWebRequestResponse(webRequest);
        dismissProgressBar();
        if (webRequest != null) {
            if (webRequest.getWebRequestId() == ID_PROFIT_LOSS_BY_MATCH) {
                updatePagerUi(View.GONE);
                handlePLByMatchIdResponse(webRequest);
                return;
            }
        }
        if (webRequest.isSuccess()) {
            switch (webRequest.getWebRequestId()) {
                case ID_ONE_PAGE_REPORT:
                    handleOnePageReportResponse(webRequest);
                    break;
            }
        } else {
            String msg = webRequest.getErrorMessageFromResponse();
            if (isValidString(msg)) {
                webRequest.showInvalidResponse(msg);
            }
        }
    }

    private void handlePLByMatchIdResponse(WebRequest webRequest) {
        if (webRequest == null) return;
        PLByMatchIdResponseModel responsePojo = webRequest.getResponsePojo(PLByMatchIdResponseModel.class);
        ProfitLossModel dataModel = webRequest.getExtraData(KEY_PROFIT_LOSS_BY_MATCH);
        if (isValidObject(responsePojo)) {

            List<PLByMatchIdModel> userPL = responsePojo.getUserPL();
            if (isValidObject(userPL) && userPL.size() > 0)
                showProfitLossByMatchIdDialog(dataModel, userPL);
        }

    }


    private void handleOnePageReportResponse(WebRequest webRequest) {
        if (webRequest == null) return;
        switch (getCurrentTypeModel().getId()) {
            case 1:
                handleBetHistoryData(webRequest);
                break;
            case 2:
                handleProfitLossData(webRequest);
                break;
            case 3:
                handleAccountStatementData(webRequest);
                break;
            case 4:
                handleLoginHistoryData(webRequest);
                break;
        }

    }

    private void handleProfitLossData(WebRequest webRequest) {
        profitLossList.clear();
        ProfitLossResponseModel responseModel = webRequest.getResponsePojo(ProfitLossResponseModel.class);
        if (!isValidObject(responseModel)) return;
        totalPage = responseModel.getTotalPages(responseModel.getCount());
        addViewForProfitLoss(responseModel);
        handlePagerUi(totalPage);
        List<ProfitLossModel> list = responseModel.getPLList();
        if (isValidObject(list) && list.size() > 0) {
            profitLossList.addAll(list);
            showNoRecordFoundUi(View.GONE);
        } else {
            showNoRecordFoundUi(View.VISIBLE);
        }
        profitLossAdapter.notifyDataSetChanged();

    }

    private void showNoRecordFoundUi(int visibility) {
        if (visibility == View.VISIBLE) {
            showCustomToast("No Record Found");
            updateViewVisibility(ll_add_view, View.GONE);
        } else {
            updateViewVisibility(ll_add_view, View.VISIBLE);
        }
        updateViewVisibility(tv_no_record_found, visibility);
    }

    private void handleAccountStatementData(WebRequest webRequest) {
        accountsList.clear();
        AccountStatementResponseModel responseModel = webRequest.getResponsePojo(AccountStatementResponseModel.class);
        if (!isValidObject(responseModel)) return;
        totalPage = responseModel.getTotalPages(responseModel.getCount());
        handlePagerUi(totalPage);
        addViewForAccountStmt(responseModel);
        List<AccountStatementModel> list = responseModel.getAccountList();
        if (isValidObject(list) && list.size() > 0) {
            accountsList.addAll(list);
            showNoRecordFoundUi(View.GONE);
        } else {
            showNoRecordFoundUi(View.VISIBLE);
        }
        accountsAdapter.notifyDataSetChanged();

    }

    private void handleBetHistoryData(WebRequest webRequest) {
        betHistoryList.clear();

        OnePageReportResponseModel responseModel = webRequest.getResponsePojo(OnePageReportResponseModel.class);
        if (!isValidObject(responseModel)) return;
        totalPage = responseModel.getTotalPages(responseModel.getCount());
        handlePagerUi(totalPage);
        addViewForMyBets(responseModel);
        List<OnePageReportModel> list = responseModel.getPageReportList();
        if (isValidObject(list) && list.size() > 0) {
            betHistoryList.addAll(list);
            showNoRecordFoundUi(View.GONE);
        } else {
            showNoRecordFoundUi(View.VISIBLE);
        }
        betHistoryAdapter.notifyDataSetChanged();
    }

    private void handlePagerUi(int totalPages) {
        if (totalPages <= 1) {
            updatePagerUi(View.GONE);
            return;
        }
        pagerAdapter.setTotalPages(currentPage, totalPages);
        updatePagerUi(View.VISIBLE);

        if (currentPage == 1) {
            tv_f_page.setTextColor(getResources().getColor(R.color.colorGray));
            tv_p_page.setTextColor(getResources().getColor(R.color.colorGray));
            tv_f_page.setEnabled(false);
            tv_p_page.setEnabled(false);
        } else {
            tv_f_page.setEnabled(true);
            tv_p_page.setEnabled(true);
            tv_f_page.setTextColor(getResources().getColor(R.color.et_text_color));
            tv_p_page.setTextColor(getResources().getColor(R.color.et_text_color));
        }
        if (totalPages == currentPage) {
            tv_n_page.setEnabled(false);
            tv_l_page.setEnabled(false);
            tv_n_page.setTextColor(getResources().getColor(R.color.colorGray));
            tv_l_page.setTextColor(getResources().getColor(R.color.colorGray));
        } else {
            tv_n_page.setEnabled(true);
            tv_l_page.setEnabled(true);
            tv_n_page.setTextColor(getResources().getColor(R.color.et_text_color));
            tv_l_page.setTextColor(getResources().getColor(R.color.et_text_color));
        }
    }

    private void handleLoginHistoryData(WebRequest webRequest) {
        ll_add_view.removeAllViews();
        loginHistoryList.clear();
        LoginHistoryResponseModel responseModel = webRequest.getResponsePojo(LoginHistoryResponseModel.class);
        if (!isValidObject(responseModel)) return;
        totalPage = responseModel.getTotalPages(responseModel.getCount());
        handlePagerUi(totalPage);
        List<LoginHistoryModel> list = responseModel.getLoginHistory();
        if (isValidObject(list) && list.size() > 0) {
            loginHistoryList.addAll(list);
            showNoRecordFoundUi(View.GONE);
        } else {
            showNoRecordFoundUi(View.VISIBLE);
        }
        loginHistoryAdapter.notifyDataSetChanged();

    }

    private void updatePagerUi(int visibility) {

        updateViewVisibility(rl_view, visibility);
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
                    case R.id.tv_type:
                        TypeModel model = (TypeModel) list.get(position);
                        if (model.getId() == 2) {
                            updateViewVisibility(tab_layout, View.VISIBLE);
                        } else {
                            updateViewVisibility(tab_layout, View.GONE);
                        }
                        break;

                }
                dialog.dismiss();

            }
        });

        dialog.show(getFragmentManager(), DataDialog.class.getSimpleName());

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

    private void addViewForMyBets(OnePageReportResponseModel model) {
        ll_add_view.removeAllViews();

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_my_bets_amount, null);

        TextView tv_grand_pl = view.findViewById(R.id.tv_grand_pl);
        TextView tv_grand_profit = view.findViewById(R.id.tv_grand_profit);
        TextView tv_grand_liability = view.findViewById(R.id.tv_grand_liability);
        TextView tv_cp_pl = view.findViewById(R.id.tv_cp_pl);
        TextView tv_cp_profit = view.findViewById(R.id.tv_cp_profit);
        TextView tv_cp_liability = view.findViewById(R.id.tv_cp_liability);

        double cp_liability = model.getCurrentPageTotalLiability();
        int cp_color_liability = model.getAmountTextColor(cp_liability);
        double cp_p_l = model.getCurrentPageTotalP_L();
        int cp_color_p_l = model.getAmountTextColor(cp_p_l);
        double cp_profit = model.getCurrentPageTotalProfit();
        int cp_color_profit = model.getAmountTextColor(cp_profit);
        double grand_p_l = model.getTot_p_l();
        int grand_color_p_l = model.getAmountTextColor(grand_p_l);
        double grand_profit = model.getTot_profit();
        int grand_color_profit = model.getAmountTextColor(grand_profit);
        double grand_liability = model.getTot_liability();
        int grand_color_liability = model.getAmountTextColor(grand_liability);

        tv_cp_pl.setTextColor(getResources().getColor(cp_color_p_l));
        tv_cp_profit.setTextColor(getResources().getColor(cp_color_profit));
        tv_cp_liability.setTextColor(getResources().getColor(cp_color_liability));
        tv_cp_pl.setText("" + cp_p_l);
        tv_cp_profit.setText("" + cp_profit);
        tv_cp_liability.setText("" + cp_liability);

        tv_grand_profit.setTextColor(getResources().getColor(grand_color_profit));
        tv_grand_pl.setTextColor(getResources().getColor(grand_color_p_l));
        tv_grand_liability.setTextColor(getResources().getColor(grand_color_liability));
        tv_grand_profit.setText("" + grand_profit);
        tv_grand_pl.setText("" + grand_p_l);
        tv_grand_liability.setText("" + grand_liability);

        ll_add_view.addView(view);

    }

    private void addViewForProfitLoss(ProfitLossResponseModel pl_model) {
        ll_add_view.removeAllViews();

        View view = getLayoutInflater().inflate(R.layout.layout_profit_loss_amount, null);
        TextView tv_grand_pl = view.findViewById(R.id.tv_grand_pl);
        TextView tv_grand_comm = view.findViewById(R.id.tv_grand_comm);

        TextView tv_cp_comm = view.findViewById(R.id.tv_cp_comm);
        TextView tv_cp_pl = view.findViewById(R.id.tv_cp_pl);


        double cp_p_l = pl_model.getCurrentPageTotalP_L();
        int cp_color_p_l = pl_model.getAmountTextColor(cp_p_l);
        double cp_comm = pl_model.getCurrentPageCommission();
        int cp_color_comm = pl_model.getAmountTextColor(cp_comm);
        double grand_p_l = pl_model.getTot_PnL();
        int grand_color_p_l = pl_model.getAmountTextColor(grand_p_l);
        double grand_comm = pl_model.getTot_Comm();
        int grand_color_comm = pl_model.getAmountTextColor(grand_comm);

        tv_cp_pl.setTextColor(getResources().getColor(cp_color_p_l));
        tv_cp_pl.setText("" + cp_p_l);
        tv_cp_comm.setTextColor(getResources().getColor(cp_color_comm));
        tv_cp_comm.setText("" + cp_comm);

        tv_grand_pl.setTextColor(getResources().getColor(grand_color_p_l));
        tv_grand_pl.setText("" + grand_p_l);
        tv_grand_comm.setTextColor(getResources().getColor(grand_color_comm));
        tv_grand_comm.setText("" + grand_comm);

        ll_add_view.addView(view);
    }

    private void addViewForAccountStmt(AccountStatementResponseModel model) {
        ll_add_view.removeAllViews();

        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.layout_acc_stmt_amount, null);

        TextView tv_cp_credit = view.findViewById(R.id.tv_cp_credit);
        TextView tv_cp_debit = view.findViewById(R.id.tv_cp_debit);
        TextView tv_cp_balance = view.findViewById(R.id.tv_cp_balance);

        double cp_balance = model.getCurrentPageTotalBalance();
        int cp_color_balance = model.getAmountTextColor(cp_balance);

        double cp_credit = model.getCurrentPageTotalCredit();
        int cp_color_credit = model.getAmountTextColor(cp_credit);

        double cp_debit = model.getCurrentPageTotalDebit();
        int cp_color_debit = model.getAmountTextColor(cp_debit);

        tv_cp_credit.setText("" + cp_credit);
        tv_cp_credit.setTextColor(getResources().getColor(cp_color_credit));

        tv_cp_debit.setText("" + cp_debit);
        //tv_cp_debit.setTextColor(getResources().getColor(cp_color_debit));

        tv_cp_balance.setText("" + cp_balance);
        tv_cp_balance.setTextColor(getResources().getColor(cp_color_balance));

        ll_add_view.addView(view);

    }
}
