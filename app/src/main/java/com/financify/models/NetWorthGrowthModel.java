package com.financify.models;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class NetWorthGrowthModel {
    private String month;
    private Double net_worth;

    public NetWorthGrowthModel(String month, Double net_worth) {
        this.month = month;
        this.net_worth = net_worth;
    }

    public String getMonth() {
        return YearMonth.parse(month).format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
    }
    public Double getNetWorth() {return net_worth;}
}
