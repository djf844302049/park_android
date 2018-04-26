package com.anyidc.cloudpark.moduel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/26.
 */

public class DrawCashBean implements Serializable {

    /**
     * order_id : 201804261602586425
     * money : 10
     * time : 1524729778
     */

    private String order_id;
    private String money;
    private long time;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public long getTime() {
        return time;
    }

    public String getDate() {
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(1000 * time));
        return format;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
