package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ParkListBean {

    /**
     * total : 7
     * park : {"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"city_id":0,"lat":"24.4629300000","lng":"118.0329000000","num":50,"bad_num":0,"thumb":"http://www.baidu.com","province":202,"city":101,"district":22,"town":2,"address":"水云湾","fee_id":1,"create_time":0,"update_time":1520838536,"category":3,"status":1,"business_time":"09:00:00","non_business_time":"18:00:00","appointment_money":6,"area_1":"潞城镇","area_2":"王佐镇","area_3":"崇文区","area_4":"水云湾","fee":{"first_time":5,"money":6,"hourly":2,"max":30}}
     * list : [{"unit_id":"A00001","parking_id":1,"camera_id":1,"status":1,"positionDesc":0,"lockid":"0","frozen_time":1523327530,"order_sn":""},{"unit_id":"A00002","parking_id":1,"camera_id":1,"status":1,"positionDesc":0,"lockid":"0","frozen_time":0,"order_sn":null},{"unit_id":"A00003","parking_id":1,"camera_id":12,"status":1,"positionDesc":1,"lockid":"65","frozen_time":1522324128,"order_sn":null},{"unit_id":"A00004","parking_id":1,"camera_id":0,"status":1,"positionDesc":1,"lockid":"456","frozen_time":0,"order_sn":null},{"unit_id":"A00005","parking_id":1,"camera_id":0,"status":1,"positionDesc":2,"lockid":"666","frozen_time":0,"order_sn":null},{"unit_id":"A00006","parking_id":1,"camera_id":999,"status":1,"positionDesc":0,"lockid":"999","frozen_time":0,"order_sn":null},{"unit_id":"A00007","parking_id":1,"camera_id":88,"status":1,"positionDesc":0,"lockid":"99","frozen_time":0,"order_sn":null}]
     * sharelist : []
     */

    private int total;
    private ParkBean park;
    private List<ListBean> list;
    private List<?> sharelist;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ParkBean getPark() {
        return park;
    }

    public void setPark(ParkBean park) {
        this.park = park;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<?> getSharelist() {
        return sharelist;
    }

    public void setSharelist(List<?> sharelist) {
        this.sharelist = sharelist;
    }

    public static class ParkBean {
        /**
         * parking_id : 1
         * parking_name : 海沧水云湾车场
         * type : 1
         * city_id : 0
         * lat : 24.4629300000
         * lng : 118.0329000000
         * num : 50
         * bad_num : 0
         * thumb : http://www.baidu.com
         * province : 202
         * city : 101
         * district : 22
         * town : 2
         * address : 水云湾
         * fee_id : 1
         * create_time : 0
         * update_time : 1520838536
         * category : 3
         * status : 1
         * business_time : 09:00:00
         * non_business_time : 18:00:00
         * appointment_money : 6
         * area_1 : 潞城镇
         * area_2 : 王佐镇
         * area_3 : 崇文区
         * area_4 : 水云湾
         * fee : {"first_time":5,"money":6,"hourly":2,"max":30}
         */

        private int parking_id;
        private String parking_name;
        private int type;
        private int city_id;
        private String lat;
        private String lng;
        private int num;
        private int bad_num;
        private String thumb;
        private int province;
        private int city;
        private int district;
        private int town;
        private String address;
        private int fee_id;
        private int create_time;
        private int update_time;
        private int category;
        private int status;
        private String business_time;
        private String non_business_time;
        private int appointment_money;
        private String area_1;
        private String area_2;
        private String area_3;
        private String area_4;
        private FeeBean fee;

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

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getBad_num() {
            return bad_num;
        }

        public void setBad_num(int bad_num) {
            this.bad_num = bad_num;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
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

        public int getFee_id() {
            return fee_id;
        }

        public void setFee_id(int fee_id) {
            this.fee_id = fee_id;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getBusiness_time() {
            return business_time;
        }

        public void setBusiness_time(String business_time) {
            this.business_time = business_time;
        }

        public String getNon_business_time() {
            return non_business_time;
        }

        public void setNon_business_time(String non_business_time) {
            this.non_business_time = non_business_time;
        }

        public int getAppointment_money() {
            return appointment_money;
        }

        public void setAppointment_money(int appointment_money) {
            this.appointment_money = appointment_money;
        }

        public String getArea_1() {
            return area_1;
        }

        public void setArea_1(String area_1) {
            this.area_1 = area_1;
        }

        public String getArea_2() {
            return area_2;
        }

        public void setArea_2(String area_2) {
            this.area_2 = area_2;
        }

        public String getArea_3() {
            return area_3;
        }

        public void setArea_3(String area_3) {
            this.area_3 = area_3;
        }

        public String getArea_4() {
            return area_4;
        }

        public void setArea_4(String area_4) {
            this.area_4 = area_4;
        }

        public FeeBean getFee() {
            return fee;
        }

        public void setFee(FeeBean fee) {
            this.fee = fee;
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

    public static class ListBean {
        /**
         * unit_id : A00001
         * parking_id : 1
         * camera_id : 1
         * status : 1
         * positionDesc : 0
         * lockid : 0
         * frozen_time : 1523327530
         * order_sn :
         */

        private String unit_id;
        private int parking_id;
        private int camera_id;
        private int status;
        private int positionDesc;
        private String lockid;
        private int frozen_time;
        private String order_sn;

        public String getUnit_id() {
            return unit_id;
        }

        public void setUnit_id(String unit_id) {
            this.unit_id = unit_id;
        }

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public int getCamera_id() {
            return camera_id;
        }

        public void setCamera_id(int camera_id) {
            this.camera_id = camera_id;
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

        public String getLockid() {
            return lockid;
        }

        public void setLockid(String lockid) {
            this.lockid = lockid;
        }

        public int getFrozen_time() {
            return frozen_time;
        }

        public void setFrozen_time(int frozen_time) {
            this.frozen_time = frozen_time;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }
    }
}
