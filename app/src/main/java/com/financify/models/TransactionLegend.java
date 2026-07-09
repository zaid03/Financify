package com.financify.models;

public class TransactionLegend {
    private String type;
    private String category;

    public TransactionLegend(String type, String category) {
        this.type = type;
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }
}