package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ParkDetailBean {

    /**
     * count_data : {"total":7,"free":6,"busy":1,"use":0}
     * park : {"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"city_id":0,"lat":"24.4629300000","lng":"118.0329000000","num":50,"bad_num":0,"thumb":"http://park.deyuelou.top/images/9e3a35a3593f86e0848266aa63fddc8f.png","province":16068,"city":16278,"district":16294,"town":2,"address":"水云湾","fee_id":1,"create_time":0,"update_time":1528189079,"category":3,"status":1,"business_time":"09:00:00","non_business_time":"18:00:00","appointment_money":6,"area_1":"福建省","area_2":"厦门市","area_3":"海沧区","area_4":"水云湾","fee":{"first_time":"0.5","money":"5","second_money":"6","night_money":"8","day_money":"60"}}
     * use_arr : []
     * free_arr : [{"unit_id":"A00002","parking_id":1,"camera_id":1,"status":1,"positionDesc":0,"lockid":"0","sub_type":0,"sub_id":0,"frozen_time":0,"order_sn":null,"lat":"","lng":"","status_key":"free"},{"unit_id":"A00003","parking_id":1,"camera_id":12,"status":1,"positionDesc":1,"lockid":"65","sub_type":0,"sub_id":0,"frozen_time":1522324128,"order_sn":null,"lat":"","lng":"","status_key":"free"},{"unit_id":"A00004","parking_id":1,"camera_id":0,"status":1,"positionDesc":1,"lockid":"456","sub_type":0,"sub_id":0,"frozen_time":0,"order_sn":null,"lat":"","lng":"","status_key":"free"},{"unit_id":"A00005","parking_id":1,"camera_id":0,"status":1,"positionDesc":2,"lockid":"666","sub_type":0,"sub_id":0,"frozen_time":1528376708,"order_sn":null,"lat":"","lng":"","status_key":"free"},{"unit_id":"A00006","parking_id":1,"camera_id":999,"status":1,"positionDesc":0,"lockid":"999","sub_type":0,"sub_id":0,"frozen_time":1528367079,"order_sn":null,"lat":"","lng":"","status_key":"free"},{"unit_id":"A00007","parking_id":1,"camera_id":88,"status":1,"positionDesc":0,"lockid":"99","sub_type":0,"sub_id":0,"frozen_time":0,"order_sn":null,"lat":"","lng":"","status_key":"free"}]
     * busy_arr : [{"unit_id":"A00001","parking_id":1,"camera_id":1,"status":4,"positionDesc":0,"lockid":"0","sub_type":0,"sub_id":0,"frozen_time":0,"order_sn":"","lat":"","lng":"","status_key":"busy"}]
     */

    private CountDataBean count_data;
    private ParkBean park;
    private List<UseArrBean> use_arr;
    private List<UseArrBean> free_arr;
    private List<UseArrBean> busy_arr;

    public CountDataBean getCount_data() {
        return count_data;
    }

    public void setCount_data(CountDataBean count_data) {
        this.count_data = count_data;
    }

    public ParkBean getPark() {
        return park;
    }

    public void setPark(ParkBean park) {
        this.park = park;
    }

    public List<UseArrBean> getUse_arr() {
        return use_arr;
    }

    public void setUse_arr(List<UseArrBean> use_arr) {
        this.use_arr = use_arr;
    }

    public List<UseArrBean> getFree_arr() {
        return free_arr;
    }

    public void setFree_arr(List<UseArrBean> free_arr) {
        this.free_arr = free_arr;
    }

    public List<UseArrBean> getBusy_arr() {
        return busy_arr;
    }

    public void setBusy_arr(List<UseArrBean> busy_arr) {
        this.busy_arr = busy_arr;
    }

    public static class CountDataBean {
        /**
         * total : 7
         * free : 6
         * busy : 1
         * use : 0
         */

        private int total;
        private int free;
        private int busy;
        private int use;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getFree() {
            return free;
        }

        public void setFree(int free) {
            this.free = free;
        }

        public int getBusy() {
            return busy;
        }

        public void setBusy(int busy) {
            this.busy = busy;
        }

        public int getUse() {
            return use;
        }

        public void setUse(int use) {
            this.use = use;
        }
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
         * thumb : http://park.deyuelou.top/images/9e3a35a3593f86e0848266aa63fddc8f.png
         * big_img : http:\/\/p2xsrupxn.bkt.clouddn.com\/2018\/07\/7ee3c201807041514379127.png
         * province : 16068
         * city : 16278
         * district : 16294
         * town : 2
         * address : 水云湾
         * fee_id : 1
         * create_time : 0
         * update_time : 1528189079
         * category : 3
         * status : 1
         * business_time : 09:00:00
         * non_business_time : 18:00:00
         * appointment_money : 6
         * area_1 : 福建省
         * area_2 : 厦门市
         * area_3 : 海沧区
         * area_4 : 水云湾
         * fee : {"first_time":"0.5","money":"5","second_money":"6","night_money":"8","day_money":"60"}
         * fee_desc:["07:30-20:00（含）30分钟（含）以内收费5元","07:30-20:00（含）30分钟以后每30分钟加收6元","20:30-22:00（含）每小时收8元","连续停放24小时最高收费标准60元"]
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
        private String big_img;
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
        private float appointment_money;
        private String area_1;
        private String area_2;
        private String area_3;
        private String area_4;
        private FeeBean fee;
        private List<String> fee_desc;

        public List<String> getFee_desc() {
            return fee_desc;
        }

        public String getBig_img() {
            return big_img;
        }

        public void setBig_img(String big_img) {
            this.big_img = big_img;
        }

        public void setFee_desc(List<String> fee_desc) {
            this.fee_desc = fee_desc;
        }

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

        public float getAppointment_money() {
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

    public static class UseArrBean {
        /**
         * unit_id : A00002
         * parking_id : 1
         * camera_id : 1
         * status : 1
         * positionDesc : 0
         * lockid : 0
         * sub_type : 0
         * sub_id : 0
         * frozen_time : 0
         * order_sn : null
         * lat :
         * lng :
         * status_key : free
         */

        private String unit_id;
        private int parking_id;
        private int camera_id;
        private int status;
        private int positionDesc;
        private String lockid;
        private int sub_type;
        private int sub_id;
        private int frozen_time;
        private Object order_sn;
        private String lat;
        private String lng;
        private String status_key;

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

        public int getSub_type() {
            return sub_type;
        }

        public void setSub_type(int sub_type) {
            this.sub_type = sub_type;
        }

        public int getSub_id() {
            return sub_id;
        }

        public void setSub_id(int sub_id) {
            this.sub_id = sub_id;
        }

        public int getFrozen_time() {
            return frozen_time;
        }

        public void setFrozen_time(int frozen_time) {
            this.frozen_time = frozen_time;
        }

        public Object getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(Object order_sn) {
            this.order_sn = order_sn;
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

        public String getStatus_key() {
            return status_key;
        }

        public void setStatus_key(String status_key) {
            this.status_key = status_key;
        }
    }
}
