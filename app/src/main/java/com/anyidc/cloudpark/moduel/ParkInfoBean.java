package com.anyidc.cloudpark.moduel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */

public class ParkInfoBean implements Serializable{

    /**
     * order : {"order_id":114,"order_sn":"JSO2018062816313182819","parking_id":1,"unit_id":"A00001","car_id":74,"user_id":11,"pay_id":0,"transaction_id":"","total_amount":"0.00","pay_status":0,"create_time":1530174691,"end_time":1530174691,"pay_time":0,"product_id":4,"update_time":0,"status":1,"share_time":null,"times":0,"settled":0}
     * fee : {"first_time":"0.5","money":"5","second_money":"6","night_money":"8","day_money":"60"}
     * pay : 5.00
     * is_share : 0
     * start_time : 1530174691
     * end_time : 1530174743
     * stay_time : 00小时00分钟52秒
     * discount_desc : VIP不定期八折优惠
     * discount_money : 108.00
     * fee_desc : ["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"]
     */

    private OrderBean order;
    private FeeBean fee;
    private String pay;
    private String discount_desc;
    private float discount_money;
    private int is_share;
    private long start_time;
    private long end_time;
    private String stay_time;
    private List<String> fee_desc;

    public String getDiscount_desc() {
        return discount_desc;
    }

    public void setDiscount_desc(String discount_desc) {
        this.discount_desc = discount_desc;
    }

    public float getDiscount_money() {
        return discount_money;
    }

    public void setDiscount_money(float discount_money) {
        this.discount_money = discount_money;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public FeeBean getFee() {
        return fee;
    }

    public void setFee(FeeBean fee) {
        this.fee = fee;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public int getIs_share() {
        return is_share;
    }

    public void setIs_share(int is_share) {
        this.is_share = is_share;
    }

    public String getStart_time() {
        String format = new SimpleDateFormat("MM/dd HH:mm").format(new Date(1000 * start_time));
        return format;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        String format = new SimpleDateFormat("MM/dd HH:mm").format(new Date(1000 * end_time));
        return format;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public String getStay_time() {
        return stay_time;
    }

    public void setStay_time(String stay_time) {
        this.stay_time = stay_time;
    }

    public List<String> getFee_desc() {
        return fee_desc;
    }

    public void setFee_desc(List<String> fee_desc) {
        this.fee_desc = fee_desc;
    }

    public static class OrderBean {
        /**
         * order_id : 114
         * order_sn : JSO2018062816313182819
         * parking_id : 1
         * unit_id : A00001
         * car_id : 74
         * user_id : 11
         * pay_id : 0
         * transaction_id :
         * total_amount : 0.00
         * pay_status : 0
         * create_time : 1530174691
         * end_time : 1530174691
         * pay_time : 0
         * product_id : 4
         * update_time : 0
         * status : 1
         * share_time : null
         * times : 0
         * settled : 0
         */

        private int order_id;
        private String order_sn;
        private int parking_id;
        private String unit_id;
        private int car_id;
        private int user_id;
        private int pay_id;
        private String transaction_id;
        private String total_amount;
        private int pay_status;
        private int create_time;
        private int end_time;
        private int pay_time;
        private int product_id;
        private int update_time;
        private int status;
        private Object share_time;
        private int times;
        private int settled;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public int getCar_id() {
            return car_id;
        }

        public void setCar_id(int car_id) {
            this.car_id = car_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getPay_id() {
            return pay_id;
        }

        public void setPay_id(int pay_id) {
            this.pay_id = pay_id;
        }

        public String getTransaction_id() {
            return transaction_id;
        }

        public void setTransaction_id(String transaction_id) {
            this.transaction_id = transaction_id;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getEnd_time() {
            return end_time;
        }

        public void setEnd_time(int end_time) {
            this.end_time = end_time;
        }

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getShare_time() {
            return share_time;
        }

        public void setShare_time(Object share_time) {
            this.share_time = share_time;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }

        public int getSettled() {
            return settled;
        }

        public void setSettled(int settled) {
            this.settled = settled;
        }
    }

    public static class FeeBean {
        /**
         * first_time : 0.5
         * money : 5
         * second_money : 6
         * night_money : 8
         * day_money : 60
         */

        private String first_time;
        private String money;
        private String second_money;
        private String night_money;
        private String day_money;

        public String getFirst_time() {
            return first_time;
        }

        public void setFirst_time(String first_time) {
            this.first_time = first_time;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSecond_money() {
            return second_money;
        }

        public void setSecond_money(String second_money) {
            this.second_money = second_money;
        }

        public String getNight_money() {
            return night_money;
        }

        public void setNight_money(String night_money) {
            this.night_money = night_money;
        }

        public String getDay_money() {
            return day_money;
        }

        public void setDay_money(String day_money) {
            this.day_money = day_money;
        }
    }
}
