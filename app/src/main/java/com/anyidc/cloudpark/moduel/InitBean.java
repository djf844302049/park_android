package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/2/10.
 */

public class InitBean {

    /**
     * id : 1
     * app_type : android
     * version : 1
     * version_code : 1.0.0
     * is_force : 0
     * apk_url : x.com/1/3.html
     * upgrade_point : 1、优化了网络数据
     * 2、增加新闻内容
     * status : 1
     * create_time : 1970-01-01 08:00:00
     * update_time : 1970-01-01 08:00:00
     * is_update : 0
     */

    private int id;
    private String app_type;
    private int version;
    private String version_code;
    private int is_force;
    private String apk_url;
    private String upgrade_point;
    private int status;
    private String create_time;
    private String update_time;
    private int is_update;
    private String download_url;

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getUpgrade_point() {
        return upgrade_point;
    }

    public void setUpgrade_point(String upgrade_point) {
        this.upgrade_point = upgrade_point;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getIs_update() {
        return is_update;
    }

    public void setIs_update(int is_update) {
        this.is_update = is_update;
    }
}
