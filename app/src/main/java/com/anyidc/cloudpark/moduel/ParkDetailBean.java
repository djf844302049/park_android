package com.anyidc.cloudpark.moduel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ParkDetailBean {
    private int total;
    private ParkInfo park;
    private ArrayList<ShareParkUnitInfo> sharelist;
    private ArrayList<ParkUnitInfoBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ParkInfo getPark() {
        return park;
    }

    public void setPark(ParkInfo park) {
        this.park = park;
    }

    public ArrayList<ShareParkUnitInfo> getSharelist() {
        return sharelist;
    }

    public void setSharelist(ArrayList<ShareParkUnitInfo> sharelist) {
        this.sharelist = sharelist;
    }

    public ArrayList<ParkUnitInfoBean> getList() {
        return list;
    }

    public void setList(ArrayList<ParkUnitInfoBean> list) {
        this.list = list;
    }
}
