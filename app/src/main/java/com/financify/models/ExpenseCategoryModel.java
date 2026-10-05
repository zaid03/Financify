package com.financify.models;

public class ExpenseCategoryModel {
    private String category;
    private Double amount;

    public ExpenseCategoryModel(String category, Double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String getCategory() {return category;}
    public Double getAmount() {return amount;}
}