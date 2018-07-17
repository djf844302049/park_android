package com.anyidc.cloudpark.moduel;

public class UnitStateBean {

    /**
     * lockid : 1
     * camera_id : 0
     * unit_id : Z0003S
     * lock_status : 1
     * abn_status : 1
     * video : http://hls.open.ys7.com/openlive/ddcd25544bb0407dafb1ea6f4f7ca02e.m3u8
     * is_able : 0
     */

    private String lockid;
    private int camera_id;
    private String unit_id;
    private int lock_status;
    private int abn_status;
    private String video;
    private int is_able;

    public String getLockid() {
        return lockid;
    }

    public void setLockid(String lockid) {
        this.lockid = lockid;
    }

    public int getCamera_id() {
        return camera_id;
    }

    public void setCamera_id(int camera_id) {
        this.camera_id = camera_id;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public int getLock_status() {
        return lock_status;
    }

    public void setLock_status(int lock_status) {
        this.lock_status = lock_status;
    }

    public int getAbn_status() {
        return abn_status;
    }

    public void setAbn_status(int abn_status) {
        this.abn_status = abn_status;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getIs_able() {
        return is_able;
    }

    public void setIs_able(int is_able) {
        this.is_able = is_able;
    }
}
