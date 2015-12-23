package com.appracks.easy_wallet.data_object;

public class BarDatas {
    float amount;
    String date;

    public BarDatas(float amount, String date) {
        this.amount = amount;
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
