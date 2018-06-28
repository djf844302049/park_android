package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ParkSearchBean {


    /**
     * total : 6
     * page_num : 1
     * lngLat : {"page":"1","size":"10","lat":"24.48126","lng":"118.042628"}
     * park : [{"parking_id":10,"parking_name":"共享停车场一","type":2,"num":0,"province":16068,"city":16278,"district":16294,"town":0,"address":"测试测试","lat":"24.479948","lng":"118.041212","status":1,"fee_id":1,"available_num":2,"fee_desc":["共享停车位收费需根据车位主设定"],"distance":0.2},{"parking_id":2,"parking_name":"泰地海西停车场","type":1,"num":60,"province":201,"city":202,"district":44,"town":2,"address":"泰地海西","lat":"24.4840500000","lng":"118.0339400000","status":1,"fee_id":1,"available_num":0,"fee_desc":["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"],"distance":0.93},{"parking_id":3,"parking_name":"海沧悦实广场停车场","type":1,"num":70,"province":205,"city":203,"district":112,"town":7,"address":"海沧悦实广场","lat":"24.4840500000","lng":"118.0339400000","status":1,"fee_id":1,"available_num":0,"fee_desc":["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"],"distance":0.93},{"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"num":50,"province":16068,"city":16278,"district":16294,"town":2,"address":"水云湾","lat":"24.4629300000","lng":"118.0329000000","status":1,"fee_id":1,"available_num":13,"fee_desc":["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"],"distance":2.26},{"parking_id":9,"parking_name":"奥术大师大","type":1,"num":0,"province":636,"city":1188,"district":1223,"town":0,"address":"驱蚊器翁","lat":"24.554819","lng":"117.969597","status":1,"fee_id":1,"available_num":0,"fee_desc":["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"],"distance":11.02},{"parking_id":8,"parking_name":"阿斯达","type":0,"num":0,"province":16068,"city":16278,"district":16280,"town":0,"address":"奥术大师大","lat":"24.559815","lng":"117.971657","status":1,"fee_id":10,"distance":11.31}]
     */

    private int total;
    private int page_num;
    private LngLatBean lngLat;
    private List<ParkBean> park;

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

    public LngLatBean getLngLat() {
        return lngLat;
    }

    public void setLngLat(LngLatBean lngLat) {
        this.lngLat = lngLat;
    }

    public List<ParkBean> getPark() {
        return park;
    }

    public void setPark(List<ParkBean> park) {
        this.park = park;
    }

    public static class LngLatBean {
        /**
         * page : 1
         * size : 10
         * lat : 24.48126
         * lng : 118.042628
         */

        private String page;
        private String size;
        private double lat;
        private double lng;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class ParkBean {
        /**
         * parking_id : 10
         * parking_name : 共享停车场一
         * type : 2
         * num : 0
         * province : 16068
         * city : 16278
         * district : 16294
         * town : 0
         * address : 测试测试
         * lat : 24.479948
         * lng : 118.041212
         * status : 1
         * fee_id : 1
         * available_num : 2
         * fee_desc : ["共享停车位收费需根据车位主设定"]
         * distance : 0.2
         */

        private int parking_id;
        private String parking_name;
        private int type;
        private int num;
        private int province;
        private int city;
        private int district;
        private int town;
        private String address;
        private double lat;
        private double lng;
        private int status;
        private int fee_id;
        private int available_num;
        private double distance;
        private List<String> fee_desc;

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public String getParking_name() {
            return parking_name;
        }

        public void setParking_name(String parking_name) {
            this.parking_name = parking_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public int getTown() {
            return town;
        }

        public void setTown(int town) {
            this.town = town;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getFee_id() {
            return fee_id;
        }

        public void setFee_id(int fee_id) {
            this.fee_id = fee_id;
        }

        public int getAvailable_num() {
            return available_num;
        }

        public void setAvailable_num(int available_num) {
            this.available_num = available_num;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public List<String> getFee_desc() {
            return fee_desc;
        }

        public void setFee_desc(List<String> fee_desc) {
            this.fee_desc = fee_desc;
        }
    }
}
