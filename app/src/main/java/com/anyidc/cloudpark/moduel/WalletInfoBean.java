package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/3/12.
 */

public class WalletInfoBean {

    /**
     * user_money : 430.01
     * deposit : 0.00
     * deposit_flag : 0
     * need_deposit:300
     */

    private String user_money;
    private String deposit;
    private int deposit_flag;
    private float need_deposit;

    public String getUser_money() {
        return user_money;
    }

    public void setUser_money(String user_money) {
        this.user_money = user_money;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public int getDeposit_flag() {
        return deposit_flag;
    }

    public void setDeposit_flag(int deposit_flag) {
        this.deposit_flag = deposit_flag;
    }

    public float getNeed_deposit() {
        return need_deposit;
    }

    public void setNeed_deposit(float need_deposit) {
        this.need_deposit = need_deposit;
    }
}
