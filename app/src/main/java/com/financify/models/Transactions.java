package com.financify.models;

public class Transactions {
    private Integer id;
    private String date;
    private String type;
    private String category;
    private String description;
    private Double amount;

    public Transactions(Integer id, String date, String type, String category, String description, Double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public Integer getId() {return id;}
    public String getDate() {return date;}
    public String getType() {return type;}
    public String getCategory() {return category;}
    public String getDescription() {return description;}
    public Double getAmount() {return amount;}
}
