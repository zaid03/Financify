package com.financify.models;

public class Transactions {
    private Integer id;
    private String date;
    private String type;
    private String category;
    private String description;
    private Double amount;
    private Integer is_big_purchase;

    public Transactions(Integer id, String date, String type, String category, String description, Double amount, Integer is_big_purchase) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.is_big_purchase = is_big_purchase;
    }

    public Integer getId() {return id;}
    public String getDate() {return date;}
    public String getType() {return type;}
    public String getCategory() {return category;}
    public String getDescription() {return description;}
    public Double getAmount() {return amount;}
    public String getIsBigPurchase() {return is_big_purchase == 1 ? "Big Purchase" : "Normal";}
}