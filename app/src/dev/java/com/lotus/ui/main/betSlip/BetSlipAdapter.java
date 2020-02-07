package com.lotus.ui.main.betSlip;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.lotus.R;
import com.lotus.model.BetSlipModel;
import com.lotus.model.StackModel;
import com.lotus.ui.MyApplication;
import com.lotus.ui.main.MainActivity;
import com.models.BaseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class BetSlipAdapter extends BaseRecycleAdapter {

    private Context context;
    private List<BetSlipModel> list;

    public BetSlipAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_bet_slip));
    }

    @Override
    public int getDataCount() {
        return list == null ? 0 : list.size();
    }

    public void update(List<BetSlipModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public View getView(int position) {
        ViewHolder viewHolder = (ViewHolder) getRecyclerView().findViewHolderForAdapterPosition(position);
        if (viewHolder != null) {
            return viewHolder.itemView;
        }
        return null;

    }

    public void onDataChange(List<BetSlipModel> betSlipModelList, BetSlipModel betSlipModel) {

    }

    private class ViewHolder extends BaseViewHolder {
        private TextView tv_type_name;
        private TextView tv_match_name;
        private ImageView iv_close;
        private TextView tv_name;
        private LinearLayout ll_layout_color;
        private EditText et_odds;
        private ImageView iv_increase_odds;
        private ImageView iv_decrease_odds;
        private EditText et_stake;
        private ImageView iv_increase_stake;
        private ImageView iv_decrease_stake;
        private LinearLayout ll_profit;
        private TextView tv_profit_text;
        private TextView tv_profit;
        private TextView tv_error;
        private TagContainerLayout tag_container_Layout;

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                BetSlipModel betSlipModel = updateAmount();
                onDataChange(null, betSlipModel);
            }
        };

        public ViewHolder(View itemView) {
            super(itemView);

            tv_type_name = itemView.findViewById(R.id.tv_type_name);
            tv_match_name = itemView.findViewById(R.id.tv_match_name);
            iv_close = itemView.findViewById(R.id.iv_close);
            ll_layout_color = itemView.findViewById(R.id.ll_layout_color);
            tv_name = itemView.findViewById(R.id.tv_name);
            et_odds = itemView.findViewById(R.id.et_odds);
            iv_increase_odds = itemView.findViewById(R.id.iv_increase_odds);
            iv_decrease_odds = itemView.findViewById(R.id.iv_decrease_odds);
            et_stake = itemView.findViewById(R.id.et_stake);
            iv_increase_stake = itemView.findViewById(R.id.iv_increase_stake);
            iv_decrease_stake = itemView.findViewById(R.id.iv_decrease_stake);
            ll_profit = itemView.findViewById(R.id.ll_profit);
            tv_profit_text = itemView.findViewById(R.id.tv_profit_text);
            tv_profit = itemView.findViewById(R.id.tv_profit);
            tv_error = itemView.findViewById(R.id.tv_error);
            updateViewVisibility(tv_error, View.GONE);
            tag_container_Layout = itemView.findViewById(R.id.tag_container_Layout);
        }

        @Override
        public void setData(int position) {
            if (list == null) return;
            et_odds.removeTextChangedListener(textWatcher);
            et_stake.removeTextChangedListener(textWatcher);

            iv_close.setTag(position);
            iv_close.setOnClickListener(this);
            BetSlipModel betSlipModel = list.get(position);
            tv_type_name.setText(betSlipModel.getType());
            tv_match_name.setText(betSlipModel.getMatchName());
            tv_name.setText(betSlipModel.getSelectionName());

            et_odds.setText(betSlipModel.getCurrentOdds());
            et_stake.setText(betSlipModel.getCurrentStack());
            tv_profit.setText(betSlipModel.getCurrentPL());

            final StackModel userStack = getMyApplication().getUserStack();
            if (userStack != null) {
                List<String> tags = new ArrayList<>(userStack.getMatch_stake());
                tags.add("Clear");
                List<int[]> tagsColor = generateTagColors(tags);
                tag_container_Layout.setTags(tags, tagsColor);
            }
            if (betSlipModel.isBack()) {
                tv_profit_text.setText("Profit");
                ll_layout_color.setBackgroundColor(context.getResources().getColor(R.color.color_back));
            } else {
                tv_profit_text.setText("Liability");
                ll_layout_color.setBackgroundColor(context.getResources().getColor(R.color.color_lay));
            }

            String errorMsg = betSlipModel.getErrorMsg();
            if (!errorMsg.isEmpty()) {
                updateViewVisibility(tv_error, View.VISIBLE);
                tv_error.setText(errorMsg);
            }

            et_odds.setTag(position);
            et_odds.addTextChangedListener(textWatcher);

            et_stake.setTag(position);
            et_stake.addTextChangedListener(textWatcher);

            if (betSlipModel.isSession()) {
                et_odds.setEnabled(false);
                updateViewVisibility(iv_increase_odds, View.GONE);
                updateViewVisibility(iv_decrease_odds, View.GONE);
                updateViewVisibility(ll_profit, View.GONE);
            } else {
                et_odds.setEnabled(true);
                updateViewVisibility(iv_increase_odds, View.VISIBLE);
                updateViewVisibility(iv_decrease_odds, View.VISIBLE);
                updateViewVisibility(ll_profit, View.VISIBLE);
            }

            iv_increase_odds.setOnClickListener(this);
            iv_decrease_odds.setOnClickListener(this);

            iv_increase_stake.setOnClickListener(this);
            iv_decrease_stake.setOnClickListener(this);

            tag_container_Layout.setOnTagClickListener(new TagView.OnTagClickListener() {
                @Override
                public void onTagClick(int position, String text) {
                    handleTagClick(userStack, position);
                }

                @Override
                public void onTagLongClick(int position, String text) {

                }

                @Override
                public void onSelectedTagDrag(int position, String text) {

                }

                @Override
                public void onTagCrossClick(int position) {

                }
            });


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.iv_increase_odds:
                    addInRate(0.01f);
                    break;

                case R.id.iv_decrease_odds:
                    addInRate(-0.01f);
                    break;

                case R.id.iv_increase_stake:
                    addInStackAmount(1);
                    break;

                case R.id.iv_decrease_stake:
                    addInStackAmount(-1);
                    break;

                default:
                    performItemClick((Integer) v.getTag(), v);
                    break;
            }

        }

        public void updateViewVisibility(View view, int visibility) {
            if (view.getVisibility() != visibility)
                view.setVisibility(visibility);
        }

        private BetSlipModel updateAmount() {
            String rate = et_odds.getText().toString().trim();
            String stackAmount = et_stake.getText().toString().trim();

            if (rate.isEmpty()) {
                rate = "0";
            }
            if (stackAmount.isEmpty()) {
                stackAmount = "0";
            }
            try {
                double rateD = Double.parseDouble(rate);
                double stackAmountD = Double.parseDouble(stackAmount);
                double totalValue = rateD * stackAmountD;
                double amount = totalValue - stackAmountD;
                tv_profit.setText(amount > 0 ? getValidDecimalFormat(amount) : getValidDecimalFormat(0.0));
            } catch (NumberFormatException e) {
                tv_profit.setText(getValidDecimalFormat(0.0));
            }

            Integer tag = (Integer) et_odds.getTag();
            BetSlipModel betSlipModel = list.get(tag.intValue());
            betSlipModel.setCurrentOdds(rate);
            betSlipModel.setCurrentStack(stackAmount);
            betSlipModel.setCurrentPL(tv_profit.getText().toString().trim());
            if (tv_error.getVisibility() == View.VISIBLE) {
                updateViewVisibility(tv_error, View.GONE);
                betSlipModel.setErrorMsg("");
            }
            return betSlipModel;
        }

        public String getValidDecimalFormat(double value) {
            return String.format(Locale.ENGLISH, "%.2f", value);
        }

        private void handleTagClick(StackModel userStack, int position) {
            if (position == userStack.getMatch_stake().size()) {
                et_stake.setText(String.valueOf(0));
            } else {
                try {
                    addInStackAmount(Long.parseLong(userStack.getMatch_stake().get(position)));
                } catch (NumberFormatException e) {

                }
            }

        }

        public void addInRate(float value) {
            String odds = et_odds.getText().toString().trim();
            if (isValidString(odds)) {
                try {
                    double newValue = Double.parseDouble(odds) + value;
                    if (newValue < 0.01) {
                        newValue = 0.01;
                    }
                    et_odds.setText(new BaseModel() {
                    }.getValidDecimalFormat(newValue));
                } catch (NumberFormatException e) {

                }
            } else {
                et_odds.setText(odds);
            }
        }

        private void addInStackAmount(long amount) {
            String previousAmount = et_stake.getText().toString().trim();
            if (previousAmount.isEmpty()) {
                previousAmount = "0";
            }
            try {
                long newAmount = Long.parseLong(previousAmount) + amount;
                if (newAmount < 0) {
                    newAmount = 0;
                }
                et_stake.setText(String.valueOf(newAmount));
            } catch (NumberFormatException e) {
                et_stake.setText(String.valueOf(0));
            }
        }

        private List<int[]> generateTagColors(List<String> tags) {
            List<int[]> tagsColor = new ArrayList<>();
            for (int i = 0; i < tags.size(); i++) {
                int[] color = new int[4];
                if (i == tags.size() - 1) {
                    color[0] = context.getResources().getColor(R.color.color_white);
//                    color[0] = context.getResources().getColor(R.color.colorPrimary);
                    color[1] = context.getResources().getColor(R.color.color_transparent_white);
                    color[2] = context.getResources().getColor(R.color.colorPrimary);
                    color[3] = context.getResources().getColor(R.color.color_white);
                } else {
//                    color[0] = context.getResources().getColor(R.color.color_blue_light_dark);
                    color[0] = context.getResources().getColor(R.color.colorPrimary);
                    color[1] = context.getResources().getColor(R.color.color_transparent_white);
                    color[2] = context.getResources().getColor(R.color.color_white);
                    color[3] = context.getResources().getColor(R.color.colorPrimary);
                }

                tagsColor.add(color);
            }
            return tagsColor;
        }

        public MyApplication getMyApplication() {
            return ((MainActivity) context).getMyApplication();
        }
    }


}
