package com.anyidc.cloudpark.moduel;

public class MonitorVideoBean {

    /**
     * camera_id : 1
     * camera_name : 车场测试摄像头
     * play_address : http://hls.open.ys7.com/openlive/ddcd25544bb0407dafb1ea6f4f7ca02e.m3u8
     * status : 1
     */

    private int camera_id;
    private String camera_name;
    private String play_address;
    private int status;

    public int getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public String getCamera_name() {
        return camera_name;
    }

    public void setCamera_name(String camera_name) {
        this.camera_name = camera_name;
    }

    public String getPlay_address() {
        return play_address;
    }

    public void setPlay_address(String play_address) {
        this.play_address = play_address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
