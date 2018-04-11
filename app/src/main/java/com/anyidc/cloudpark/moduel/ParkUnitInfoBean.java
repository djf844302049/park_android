package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ParkUnitInfoBean {
    private String unit_id;
    private int parking_id;
    private int camera_id;
    private int status;
    private int positionDesc;
    private String lockid;
    private String order_sn;
    private int frozen_time;

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public int getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(int positionDesc) {
        this.positionDesc = positionDesc;
    }

    public String getLockid() {
        return lockid;
    }

    public void setLockid(String lockid) {
        this.lockid = lockid;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getFrozen_time() {
        return frozen_time;
    }

    public void setFrozen_time(int frozen_time) {
        this.frozen_time = frozen_time;
    }
}
