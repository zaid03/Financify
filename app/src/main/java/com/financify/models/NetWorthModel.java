package com.financify.models;

public class NetWorthModel {
    private Integer id;
    private String month;
    private String bankBalance;
    private String loans;

    public NetWorthModel (Integer id, String month, String bankBalance, String loans) {
        this.id = id;
        this.month = month;
        this.bankBalance = bankBalance;
        this.loans = loans;
    }

    public Integer getId() {return id;}
    public String getMonth() {return month;}
    public String getBankBalance() {return bankBalance;}
    public String getLoans() {return loans;}
}
