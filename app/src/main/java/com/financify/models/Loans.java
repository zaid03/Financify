package com.financify.models;

public class Loans {
    private Integer id;
    private String name;
    private String description;
    private String Source;
    private Double amount;
    private Double Remaining;
    private Double Monthly;
    private String Start_date;
    private String Due_date;
    private Integer is_active;
    private String completionDate;

    public Loans(Integer id, String name, String description, String Source, Double amount, Double Remaining,  Double Monthly, String Start_date, String Due_date, Integer is_active, String completionDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.Source = Source;
        this.amount = amount;
        this.Remaining = Remaining;
        this.Monthly = Monthly;
        this.Start_date = Start_date;
        this.Due_date = Due_date;
        this.is_active = is_active;
        this.completionDate = completionDate;
    }

    public Integer getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
    public String getSource() {return Source;}
    public Double getAmount() {return amount;}
    public Double getRemaining() {return Remaining;}
    public Double getMonthly() {return Monthly;}
    public String getStart_date() {return Start_date;}
    public String getDue_date() {return Due_date;}
    public Integer getIs_active() {return is_active;}
    public String getCompletionDate() {return completionDate;}
}
