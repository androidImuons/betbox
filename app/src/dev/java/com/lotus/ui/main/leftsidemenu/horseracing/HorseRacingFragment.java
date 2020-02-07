package com.lotus.ui.main.leftsidemenu.horseracing;


import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lotus.R;
import com.lotus.appBase.AppBaseFragment;
import com.lotus.ui.main.leftsidemenu.horseracing.adapter.RaceAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorseRacingFragment extends AppBaseFragment {

    private TextView tvTitle;
    private RecyclerView rv_races;

    private int type;

    public void setGameType(int type) {
        this.type = type;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_horse_racing;
    }

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        rv_races = getView().findViewById(R.id.rv_races);
        tvTitle = getView().findViewById(R.id.tvTitle);

        setUpTitle();
        intializeInPlayRecyclerView();

    }

    private void setUpTitle() {
        switch (type) {
            case 1:
                tvTitle.setText("Horse Racing");
                break;
            case 2:
                tvTitle.setText("Greyhound Racing");
                break;
        }
    }


    private void intializeInPlayRecyclerView() {
        rv_races.setLayoutManager(getVerticalLinearLayoutManager());
        RaceAdapter raceAdapter = new RaceAdapter();
        rv_races.setAdapter(raceAdapter);
    }
}
