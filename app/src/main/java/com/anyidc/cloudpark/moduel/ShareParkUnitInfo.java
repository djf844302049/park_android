package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ShareParkUnitInfo {
    private String unit_id;
    private int user_id;
    private String lock_id;
    private int fee_id;
    private String share_time;
    private int status;
    private int positionDesc;
    private int parking_id;
    private int overdose;
    private int bank_card;
    private String order_sn;
    private int frozen_time;
    private ShareFeeInfo fee;

    public class ShareFeeInfo{
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

    public int getFee_id() {
        return fee_id;
    }

    public void setFee_id(int fee_id) {
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

    public int getPositionDesc() {
        return positionDesc;
    }

    public void setPositionDesc(int positionDesc) {
        this.positionDesc = positionDesc;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
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

    public ShareFeeInfo getFee() {
        return fee;
    }

    public void setFee(ShareFeeInfo fee) {
        this.fee = fee;
    }
}
