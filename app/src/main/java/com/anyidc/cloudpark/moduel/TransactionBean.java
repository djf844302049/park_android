package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionBean {


    /**
     * total : 1
     * page_num : 1
     * list : [{"id":2,"user_id":11,"order_sn":null,"amount":"-1.00","create_time":1520328597,"paid_time":1520328597,"product_id":1,"desc":"余额提现","pay_type":3,"process_type":null,"status":1}]
     */

    private int total;
    private int page_num;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 2
         * user_id : 11
         * order_sn : null
         * amount : -1.00
         * create_time : 1520328597
         * paid_time : 1520328597
         * product_id : 1
         * desc : 余额提现
         * pay_type : 3
         * process_type : null
         * status : 1
         */

        private int id;
        private int user_id;
        private Object order_sn;
        private String amount;
        private long create_time;
        private long paid_time;
        private int product_id;
        private String desc;
        private int pay_type;
        private Object process_type;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public Object getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(Object order_sn) {
            this.order_sn = order_sn;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public long getCreate_time() {
            return 1000*create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public long getPaid_time() {
            return 1000*paid_time;
        }

        public void setPaid_time(long paid_time) {
            this.paid_time = paid_time;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public Object getProcess_type() {
            return process_type;
        }

        public void setProcess_type(Object process_type) {
            this.process_type = process_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
