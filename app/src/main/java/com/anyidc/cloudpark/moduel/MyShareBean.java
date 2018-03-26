package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * 我的共享车位
 * Created by Administrator on 2018/3/22.
 */

public class MyShareBean {

    private int total;
    private int page_num;
    private List<ParkBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage_num() {
        return page_num;
    }

    public void setPage_num(int page_num) {
        this.page_num = page_num;
    }

    public List<ParkBean> getList() {
        return list;
    }

    public void setList(List<ParkBean> list) {
        this.list = list;
    }

    public static class ParkBean{

        private String unit_id;
        private int user_id;
        private String lock_id;
        private String fee_id;
        private String share_time;
        private int status;
        private int parking_id;
        private int positionDesc;
        private int overdose;
        private int bank_card;
        private int order_sn;
        private int frozen_time;

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getLock_id() {
            return lock_id;
        }

        public void setLock_id(String lock_id) {
            this.lock_id = lock_id;
        }

        public String getFee_id() {
            return fee_id;
        }

        public void setFee_id(String fee_id) {
            this.fee_id = fee_id;
        }

        public String getShare_time() {
            return share_time;
        }

        public void setShare_time(String share_time) {
            this.share_time = share_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public int getPositionDesc() {
            return positionDesc;
        }

        public void setPositionDesc(int positionDesc) {
            this.positionDesc = positionDesc;
        }

        public int getOverdose() {
            return overdose;
        }

        public void setOverdose(int overdose) {
            this.overdose = overdose;
        }

        public int getBank_card() {
            return bank_card;
        }

        public void setBank_card(int bank_card) {
            this.bank_card = bank_card;
        }

        public int getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(int order_sn) {
            this.order_sn = order_sn;
        }

        public int getFrozen_time() {
            return frozen_time;
        }

        public void setFrozen_time(int frozen_time) {
            this.frozen_time = frozen_time;
        }
    }
}
