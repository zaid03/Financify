package com.financify.models;

public class DashboardStat {
    private String metric;
    private String value;

    public DashboardStat(String metric, String value) {
        this.metric = metric;
        this.value = value;
    }

    public String getMetric() {return metric;}
    public String getValue() {return value;}
}