package com.appracks.easy_wallet.data_object;

/**
 * Created by HABIB on 12/9/2015.
 */
public class StatementData {
    private String date;
    private String sourceWay;
    private String description;
    private double amount;
    private String type;

    public StatementData(String date, String sourceWay, String description, double amount, String type) {
        this.date = date;
        this.sourceWay = sourceWay;
        this.description = description;
        this.amount = amount;
        this.type=type;
    }

    public String getType() {
        return type;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceWay() {
        return sourceWay;
    }

    public void setSourceWay(String sourceWay) {
        this.sourceWay = sourceWay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
