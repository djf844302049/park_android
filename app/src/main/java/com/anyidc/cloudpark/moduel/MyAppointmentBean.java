package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyAppointmentBean {

    /**
     * total : 4
     * page_num : 4
     * list : [{"order_id":124,"order_sn":"YYO2018070509152946917","parking_id":1,"unit_id":"A00005","car_id":55,"user_id":11,"pay_id":1,"transaction_id":"2018070521001004540553217269","total_amount":"0.01","pay_status":1,"create_time":1530753349,"end_time":1530755149,"pay_time":1530753349,"product_id":3,"update_time":0,"status":0,"share_time":null,"times":1800,"settled":0,"park":{"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"city_id":0,"lat":"24.4629300000","lng":"118.0329000000","num":50,"bad_num":0,"thumb":"http://p2xsrupxn.bkt.clouddn.com/2018/07/ee3a9201807041514509205.png","original":"2018/07/7ee3c201807041514379127.png","province":16068,"city":16278,"district":16294,"town":2,"address":"水云湾","fee_id":1,"create_time":0,"update_time":1530688870,"category":3,"status":1,"business_time":"09:00:00","non_business_time":"18:00:00","appointment_money":6,"big_img":"http://p2xsrupxn.bkt.clouddn.com/2018/07/7ee3c201807041514379127.png","area_1":"福建省","area_2":"厦门市","area_3":"海沧区","area_4":"水云湾"}}]
     */

    private int total;
    private int page_num;
    private List<AppointmentBean> list;

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

    public List<AppointmentBean> getList() {
        return list;
    }

    public void setList(List<AppointmentBean> list) {
        this.list = list;
    }

    public static class AppointmentBean {
        /**
         * order_id : 124
         * order_sn : YYO2018070509152946917
         * parking_id : 1
         * unit_id : A00005
         * car_id : 55
         * user_id : 11
         * pay_id : 1
         * transaction_id : 2018070521001004540553217269
         * total_amount : 0.01
         * pay_status : 1
         * create_time : 1530753349
         * end_time : 1530755149
         * pay_time : 1530753349
         * product_id : 3
         * update_time : 0
         * status : 0
         * share_time : null
         * times : 1800
         * settled : 0
         * park : {"parking_id":1,"parking_name":"海沧水云湾车场","type":1,"city_id":0,"lat":"24.4629300000","lng":"118.0329000000","num":50,"bad_num":0,"thumb":"http://p2xsrupxn.bkt.clouddn.com/2018/07/ee3a9201807041514509205.png","original":"2018/07/7ee3c201807041514379127.png","province":16068,"city":16278,"district":16294,"town":2,"address":"水云湾","fee_id":1,"create_time":0,"update_time":1530688870,"category":3,"status":1,"business_time":"09:00:00","non_business_time":"18:00:00","appointment_money":6,"big_img":"http://p2xsrupxn.bkt.clouddn.com/2018/07/7ee3c201807041514379127.png","area_1":"福建省","area_2":"厦门市","area_3":"海沧区","area_4":"水云湾"}
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
        private String share_time;
        private int times;
        private int settled;
        private ParkBean park;

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

        public String getShare_time() {
            return share_time;
        }

        public void setShare_time(String share_time) {
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

        public ParkBean getPark() {
            return park;
        }

        public void setPark(ParkBean park) {
            this.park = park;
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
             * thumb : http://p2xsrupxn.bkt.clouddn.com/2018/07/ee3a9201807041514509205.png
             * original : 2018/07/7ee3c201807041514379127.png
             * province : 16068
             * city : 16278
             * district : 16294
             * town : 2
             * address : 水云湾
             * fee_id : 1
             * create_time : 0
             * update_time : 1530688870
             * category : 3
             * status : 1
             * business_time : 09:00:00
             * non_business_time : 18:00:00
             * appointment_money : 6
             * big_img : http://p2xsrupxn.bkt.clouddn.com/2018/07/7ee3c201807041514379127.png
             * area_1 : 福建省
             * area_2 : 厦门市
             * area_3 : 海沧区
             * area_4 : 水云湾
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
            private String original;
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
            private String big_img;
            private String area_1;
            private String area_2;
            private String area_3;
            private String area_4;

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

            public String getOriginal() {
                return original;
            }

            public void setOriginal(String original) {
                this.original = original;
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

            public String getBig_img() {
                return big_img;
            }

            public void setBig_img(String big_img) {
                this.big_img = big_img;
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
        }
    }
}
