package com.anyidc.cloudpark.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.BaseFragmentAdapter;
import com.anyidc.cloudpark.fragment.AppointmentCompleteFragment;
import com.anyidc.cloudpark.fragment.AppointmentIngFragment;
import com.anyidc.cloudpark.utils.ViewUtils;
import com.ky.indicator.CommonNavigator;
import com.ky.indicator.CommonNavigatorAdapter;
import com.ky.indicator.IPagerIndicator;
import com.ky.indicator.IPagerTitleView;
import com.ky.indicator.LinePagerIndicator;
import com.ky.indicator.MagicIndicator;
import com.ky.indicator.ScaleTransitionPagerTitleView;
import com.ky.indicator.SimplePagerTitleView;
import com.ky.indicator.ViewPagerHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentRecordActivity extends BaseActivity {
    private MagicIndicator mIndicator;
    private ViewPager mViewPager;
    private String[] TITLE = new String[2];
    private List<String> mDataList;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_appointment_record;
    }

    @Override
    protected void initData() {
        initTitle("预约记录");
        mIndicator =  findViewById(R.id.magic_indicator_my_appointment);
        mViewPager =  findViewById(R.id.vp_my_appointment);
        initIndicator();
        initPager();
    }

    private void initPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        AppointmentIngFragment appointmentIngFragment = new AppointmentIngFragment();
        fragments.add(appointmentIngFragment);

        AppointmentCompleteFragment appointmentCompleteFragment = new AppointmentCompleteFragment();
        fragments.add(appointmentCompleteFragment);

        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(2);
    }

    private void initIndicator() {
        TITLE = getResources().getStringArray(R.array.appointment_title_array);
        mDataList = Arrays.asList(TITLE);
        CommonNavigator commonNavigator = new CommonNavigator(AppointmentRecordActivity.this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(16);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.white));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.white));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.2f));
                indicator.setLineHeight(ViewUtils.dip2px(context, 1));
                indicator.setColors(getResources().getColor(R.color.white));
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        final LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return ViewUtils.dip2px(AppointmentRecordActivity.this,ViewUtils.dip2px(AppointmentRecordActivity.this,20));
            }
        });
        ViewPagerHelper.bind(mIndicator, mViewPager);
    }
}
