package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/3/1.
 */

public class MyCarBean {


    /**
     * id : 4
     * car_no : 闽D31612
     * new_energy : 0
     * status : 0
     */

    private int id;
    private String car_no;
    private int new_energy;
    private int status;
    private boolean isEdit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no;
    }

    public int getNew_energy() {
        return new_energy;
    }

    public void setNew_energy(int new_energy) {
        this.new_energy = new_energy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
