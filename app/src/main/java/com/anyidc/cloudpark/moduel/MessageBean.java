package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/9.
 */

public class MessageBean {


    /**
     * total : 2
     * page_num : 1
     * order : [{"message_id":94,"data":"注用户们新年快乐！","send_time":1596470375,"category":2},{"message_id":93,"data":"您预约了下午5点的停车位成功","send_time":1596470375,"category":0}]
     */

    private int total;
    private int page_num;
    private List<OrderBean> order;

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

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public static class OrderBean {
        /**
         * message_id : 94
         * data : 注用户们新年快乐！
         * send_time : 1596470375
         * category : 2
         * cate_name : 预约结算
         */

        private int message_id;
        private String data;
        private long send_time;
        private int category;
        private String cate_name;

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public long getSend_time() {
            return send_time * 1000;
        }

        public void setSend_time(long send_time) {
            this.send_time = send_time;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }
    }
}
