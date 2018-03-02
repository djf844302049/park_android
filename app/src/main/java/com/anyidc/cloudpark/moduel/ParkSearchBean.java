package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class ParkSearchBean {

    /**
     * total : 4
     * page_num : 1
     * lngLat : {"lng":"118.048591","lat":"24.484162"}
     * park : [{"parking_id":4,"parking_name":"东屿停车场","type":0,"num":70,"province":205,"city":203,"district":112,"town":7,"address":"海沧悦实广","lat":"24.4840500000","lng":"118.0339400000","status":1,"available_num":0,"distance":1.48},{"parking_id":3,"parking_name":"海沧悦实广场停车场","type":1,"num":70,"province":205,"city":203,"district":112,"town":7,"address":"海沧悦实广","lat":"24.4840500000","lng":"118.0339400000","status":1,"available_num":0,"distance":1.48},{"parking_id":2,"parking_name":"泰地海西停车场","type":1,"num":60,"province":201,"city":202,"district":44,"town":2,"address":"泰地海西","lat":"24.4840500000","lng":"118.0339400000","status":1,"available_num":0,"distance":1.48},{"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"num":50,"province":202,"city":101,"district":22,"town":2,"address":"水云湾","lat":"24.4629300000","lng":"118.0329000000","status":1,"available_num":2,"distance":2.85}]
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
         * lng : 118.048591
         * lat : 24.484162
         */

        private double lng;
        private double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class ParkBean {
        /**
         * parking_id : 4
         * parking_name : 东屿停车场
         * type : 0
         * num : 70
         * province : 205
         * city : 203
         * district : 112
         * town : 7
         * address : 海沧悦实广
         * lat : 24.4840500000
         * lng : 118.0339400000
         * status : 1
         * available_num : 0
         * distance : 1.48
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
        private int available_num;
        private double distance;

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
    }
}
