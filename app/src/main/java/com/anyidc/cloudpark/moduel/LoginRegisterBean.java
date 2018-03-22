package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/2/10.
 */

public class LoginRegisterBean {

    /**
     * user_id : 11
     * token : BUl+GDW2a8bgKoJGSOND+yDRBN8HPMFAh/n9iSW1yn0b7oyxhEgM/0KR+4l2lwDn
     * is_manager : 0
     * username : 哇哈哈
     * surplus_password : 1
     * free_password : 0
     * header_img : http://p2xsrupxn.bkt.clouddn.com/2018/03/3a4c6201803070955425194.png
     * sex : 1
     * mobile : 15259166384
     */

    private int user_id;
    private String token;
    private int is_manager;
    private String username;
    private int surplus_password;
    private int free_password;
    private String header_img;
    private int sex;
    private String mobile;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIs_manager() {
        return is_manager;
    }

    public void setIs_manager(int is_manager) {
        this.is_manager = is_manager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSurplus_password() {
        return surplus_password;
    }

    public void setSurplus_password(int surplus_password) {
        this.surplus_password = surplus_password;
    }

    public int getFree_password() {
        return free_password;
    }

    public void setFree_password(int free_password) {
        this.free_password = free_password;
    }

    public String getHeader_img() {
        return header_img;
    }

    public void setHeader_img(String header_img) {
        this.header_img = header_img;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
