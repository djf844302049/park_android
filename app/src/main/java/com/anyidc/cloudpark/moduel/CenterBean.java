package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/2/24.
 */

public class CenterBean {

    /**
     * user_money : 0.00
     * username : 哇哈哈
     * isAuth : {"isAuth":2}
     */

    private String user_money;
    private String username;
    private IsAuthBean isAuth;

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public IsAuthBean getIsAuth() {
        return isAuth;
    }

    public void setIsAuth(IsAuthBean isAuth) {
        this.isAuth = isAuth;
    }

    public static class IsAuthBean {
        /**
         * isAuth : 2
         */

        private int isAuth;

        public int getIsAuth() {
            return isAuth;
        }

        public void setIsAuth(int isAuth) {
            this.isAuth = isAuth;
        }
    }
}
