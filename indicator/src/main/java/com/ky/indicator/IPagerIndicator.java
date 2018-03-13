package com.ky.indicator;

import java.util.List;

/**
 * 抽象的viewpager指示器，适用于CommonNavigator
 * Created by wangzhengshan on 2016/11/2.
 */

public interface IPagerIndicator {
    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);

    void onPositionDataProvide(List<PositionData> dataList);
}
