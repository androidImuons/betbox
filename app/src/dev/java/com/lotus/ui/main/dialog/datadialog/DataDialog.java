package com.lotus.ui.main.dialog.datadialog;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseDialogFragment;
import com.lotus.ui.main.dialog.datadialog.adapter.DataAdapter;
import com.utilities.ItemClickSupport;

import java.util.List;

public class DataDialog<T> extends AppBaseDialogFragment {

    private RecyclerView recycler_view;
    private TextView tv_title;

    private List<T> dataList;
    private OnItemSelectedListener onItemSelectedListeners;
    String title;


    public void setList(List<T> list) {
        this.dataList = list;
    }

    public void setOnItemSelectedListeners(OnItemSelectedListener onItemSelectedListeners) {
        this.onItemSelectedListeners = onItemSelectedListeners;
    }

    public void setTitle(String title) {
        this.title = title;
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
        if (title != null)
            tv_title.setText(title);
        if (dataList != null && dataList.size() > 0)
            initializeRecyclerView();
    }

    private void initializeRecyclerView() {
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(new DataAdapter(dataList));
        ItemClickSupport.addTo(recycler_view).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                onItemSelectedListeners.onItemSelectedListener(position);
            }
        });
    }

    public interface OnItemSelectedListener {
        void onItemSelectedListener(int position);
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

}
