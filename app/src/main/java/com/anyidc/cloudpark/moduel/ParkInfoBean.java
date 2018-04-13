package com.anyidc.cloudpark.moduel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/4/13.
 */

public class ParkInfoBean {


    /**
     * order : {"order_id":51,"order_sn":"2018041314193820494","parking_id":1,"unit_id":"A00001","user_id":0,"pay_id":0,"transaction_id":"","total_amount":"0.00","pay_status":0,"create_time":1523600378,"end_time":0,"pay_time":0,"product_id":4,"update_time":0,"status":0,"share_time":null,"times":0}
     * fee : {"first_time":5,"money":6,"hourly":2,"max":30}
     * now : 1523605825
     * pay : 6
     */

    private OrderBean order;
    private FeeBean fee;
    private long now;
    private String pay;

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
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

    public String getNow() {
        String format = new SimpleDateFormat("HH:mm").format(new Date(1000 * now));
        return format;
    }

    public String getDuration() {
        long duration = 1000 * now - 1000 * order.create_time;
        long longHours = duration / (60 * 60 * 1000); //根据时间差来计算小时数
        long longMinutes = (duration - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数
        long longSeconds = (duration - longHours * (60 * 60 * 1000) - longMinutes * (60 * 1000)) / 1000;//根据时间差来计算秒数
        if (longHours != 0) {
            if (longSeconds != 0) {
                return longHours + "小时" + longMinutes + "分" + longSeconds + "秒";
            } else {
                return longHours + "小时" + longMinutes + "分";
            }
        } else if (longMinutes != 0) {
            if (longSeconds != 0) {
                return longMinutes + "分" + longSeconds + "秒";
            } else {
                return longMinutes + "分";
            }
        } else if (longSeconds != 0) {
            return longSeconds + "秒";
        }
        return 0 + "分" + 0 + "秒";
    }

    public void setNow(long now) {
        this.now = now;
    }

    public static class OrderBean {
        /**
         * order_id : 51
         * order_sn : 2018041314193820494
         * parking_id : 1
         * unit_id : A00001
         * user_id : 0
         * pay_id : 0
         * transaction_id :
         * total_amount : 0.00
         * pay_status : 0
         * create_time : 1523600378
         * end_time : 0
         * pay_time : 0
         * product_id : 4
         * update_time : 0
         * status : 0
         * share_time : null
         * times : 0
         */

        private int order_id;
        private String order_sn;
        private int parking_id;
        private String unit_id;
        private int user_id;
        private int pay_id;
        private String transaction_id;
        private String total_amount;
        private int pay_status;
        private long create_time;
        private int end_time;
        private int pay_time;
        private int product_id;
        private int update_time;
        private int status;
        private Object share_time;
        private int times;

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

        public String getCreate_time() {
            String format = new SimpleDateFormat("HH:mm").format(new Date(1000 * create_time));
            return format;
        }

        public void setCreate_time(long create_time) {
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
    }

    public static class FeeBean {
        /**
         * first_time : 5
         * money : 6
         * hourly : 2
         * max : 30
         */

        private int first_time;
        private int money;
        private int hourly;
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

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
}
