package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * 我的共享车位
 * Created by Administrator on 2018/3/22.
 */

public class MyShareBean {

    private int total;
    private int page_num;
    private List<ShareParkBean> list;

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

    public List<ShareParkBean> getList() {
        return list;
    }

    public void setList(List<ShareParkBean> list) {
        this.list = list;
    }

    public static class ShareParkBean {

        private String unit_id;
        private int user_id;
        private String lock_id;
        private String fee_id;
        private String share_time;
        private int status;
        private int parking_id;
        private int positionDesc;
        private int overdose;
        private int bank_card;
        private int order_sn;
        private int frozen_time;
        private FeeInfo fee;
        private ParkInfo park;

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

        public String getLock_id() {
            return lock_id;
        }

        public void setLock_id(String lock_id) {
            this.lock_id = lock_id;
        }

        public String getFee_id() {
            return fee_id;
        }

        public void setFee_id(String fee_id) {
            this.fee_id = fee_id;
        }

        public String getShare_time() {
            return share_time;
        }

        public void setShare_time(String share_time) {
            this.share_time = share_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public int getPositionDesc() {
            return positionDesc;
        }

        public void setPositionDesc(int positionDesc) {
            this.positionDesc = positionDesc;
        }

        public int getOverdose() {
            return overdose;
        }

        public void setOverdose(int overdose) {
            this.overdose = overdose;
        }

        public int getBank_card() {
            return bank_card;
        }

        public void setBank_card(int bank_card) {
            this.bank_card = bank_card;
        }

        public int getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(int order_sn) {
            this.order_sn = order_sn;
        }

        public int getFrozen_time() {
            return frozen_time;
        }

        public void setFrozen_time(int frozen_time) {
            this.frozen_time = frozen_time;
        }

        public FeeInfo getFee() {
            return fee;
        }

        public void setFee(FeeInfo fee) {
            this.fee = fee;
        }

        public ParkInfo getPark() {
            return park;
        }

        public void setPark(ParkInfo park) {
            this.park = park;
        }
    }

    public static class FeeInfo{

        private int first_time;
        private int money;
        private int hourly;
        private int share_extra;
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

        public int getShare_extra() {
            return share_extra;
        }

        public void setShare_extra(int share_extra) {
            this.share_extra = share_extra;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }
    }
    public static class ParkInfo{

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
    }

}
