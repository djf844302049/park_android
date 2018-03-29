package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/2/24.
 */

public class UsualQuestionBean {

    /**
     * id : 4
     * title : 如果损坏了停车设备，用户具体是怎么赔偿的呢？
     * create_time : 1518159853
     * url : http://park.deyuelou.top/api/v1.answer/getmsg/id/4
     */

    private int id;
    private String title;
    private int create_time;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
