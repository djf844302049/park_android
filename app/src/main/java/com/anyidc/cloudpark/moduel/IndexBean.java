package com.anyidc.cloudpark.moduel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/27.
 */

public class IndexBean {


    private List<SlideBean> slide;
    private List<MessageBean> message;

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class SlideBean {
        /**
         * title : 儿童公益传递计划6
         * image : http://park.deyuelou.top/images/timg.jpg
         * url : http://www.uhabc.com/newsitem/278091369
         */

        private String title;
        private String image;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class MessageBean {
        /**
         * message_id : 92
         * message : 福利彩票大抽奖
         */

        private int message_id;
        private String message;

        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
