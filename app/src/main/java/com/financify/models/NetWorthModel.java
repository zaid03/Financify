package com.financify.models;

public class NetWorthModel {
    private Integer id;
    private String month;
    private Double bank_balance;
    private Integer loans;

    public NetWorthModel (Integer id, String month, Double bank_balance, Integer loans) {
        this.id = id;
        this.month = month;
        this.bank_balance = bank_balance;
        this.loans = loans;
    }

    public Integer getId() {return id;}
    public String getMonth() {return month;}
    public Integer getYear() {return Integer.parseInt(month.substring(0, 4));}
    public Integer getMonthNumber() {return Integer.parseInt(month.substring(5, 7));}
    public Double getBankBalance() {return bank_balance;}
    public Integer getLoans() {return loans;}
    public double getNetWorth() {return bank_balance - loans;}
}
