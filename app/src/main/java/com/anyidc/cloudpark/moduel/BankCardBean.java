package com.anyidc.cloudpark.moduel;

/**
 * Created by Administrator on 2018/3/26.
 */

public class BankCardBean {

    /**
     * bank_id : 13
     * bank : 建设银行-借记卡
     * card : 6236681930007058298
     */

    private String bank;
    private String card;
    private int bank_id;
    private boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getCardInfo() {
        String[] split = getBank().split("-");
        String card = getCard();
        String substring = card.substring(card.length() - 4);
        StringBuilder builder = new StringBuilder(split[0]);
        builder.append(split[split.length - 1]);
        builder.append("（");
        builder.append(substring);
        builder.append("）");
        return builder.toString();
    }

    public String getCardBank() {
        String[] split = getBank().split("-");
        return split[0];
    }

    public String getCardBankType() {
        String[] split = getBank().split("-");
        return split[split.length - 1];
    }

    public String getCardLastNums() {
        return card.substring(card.length() - 4);
    }

    public int getBank_id() {
        return bank_id;
    }

    public void setBank_id(int bank_id) {
        this.bank_id = bank_id;
    }
}
