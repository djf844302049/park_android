package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * 我的共享车位
 * Created by Administrator on 2018/3/22.
 */

public class MyShareBean {

    private int total;
    private int page_num;
    private List<ShareParkBean> list;

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

    public List<ShareParkBean> getList() {
        return list;
    }

    public void setList(List<ShareParkBean> list) {
        this.list = list;
    }

    public static class ShareParkBean {

        private String unit_id;
        private int user_id;
        private String lock_id;
        private String fee_id;
        private String share_time;
        /**status
         * 1。空闲。
         2.正在使用。
         0.坏掉
         */
        private int status;
        private int parking_id;
        private int positionDesc;
        private int overdose;
        private int bank_card;
        private int order_sn;
        private int frozen_time;
        private FeeInfo fee;
        private ParkInfo park;

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

        public FeeInfo getFee() {
            return fee;
        }

        public void setFee(FeeInfo fee) {
            this.fee = fee;
        }

        public ParkInfo getPark() {
            return park;
        }

        public void setPark(ParkInfo park) {
            this.park = park;
        }
    }

    public static class FeeInfo{

        private int first_time;
        private int money;
        private int hourly;
        private int share_extra;
        private int max;

        public int getFirst_time() {
            return first_time;
        }

        public void setFirst_time(int first_time) {
            this.first_time = first_time;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getHourly() {
            return hourly;
        }

        public void setHourly(int hourly) {
            this.hourly = hourly;
        }

        public int getShare_extra() {
            return share_extra;
        }

        public void setShare_extra(int share_extra) {
            this.share_extra = share_extra;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }

}
