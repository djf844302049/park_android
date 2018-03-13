package com.ky.indicator;

/**
 * 可测量内容区域的指示器标题
 * Created by wangzhengshan on 2016/11/2.
 */

public interface IMeasurablePagerTitleView extends IPagerTitleView {
    int getContentLeft();

    int getContentTop();

    int getContentRight();

    int getContentBottom();
}