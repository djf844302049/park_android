package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionBean {

    /**
     * total : 1
     * page_num : 1
     * list : [{"amount":"-1.00","paid_time":1520328597,"process_type":1,"desc":"余额提现","pay_id":3}]
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
         * amount : -1.00
         * paid_time : 1520328597
         * process_type : 1
         * desc : 余额提现
         * pay_id : 3
         */

        private String amount;
        private long paid_time;
        private int process_type;
        private String desc;
        private int pay_id;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public long getPaid_time() {
            return 1000 * paid_time;
        }

        public void setPaid_time(int paid_time) {
            this.paid_time = paid_time;
        }

        public int getProcess_type() {
            return process_type;
        }

        public void setProcess_type(int process_type) {
            this.process_type = process_type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getPay_id() {
            return pay_id;
        }

        public void setPay_id(int pay_id) {
            this.pay_id = pay_id;
        }
    }
}
