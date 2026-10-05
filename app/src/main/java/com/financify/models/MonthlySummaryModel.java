package com.financify.models;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MonthlySummaryModel {
    private String month;
    private Double income;
    private Double expenses;
    private Double majorExpenses;
    private Double savingRate;
    private Double globalRate;

    public MonthlySummaryModel(String month, Double income, Double expenses, Double majorExpenses, Double savingRate, Double globalRate) {
        this.month = month;
        this.income = income;
        this.expenses = expenses;
        this.majorExpenses = majorExpenses;
        this.savingRate = savingRate;
        this.globalRate = globalRate;
    }

    public String getMonth() {
        return YearMonth.parse(month).format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH));
    }
    public Double getIncome() {return income;}
    public Double getExpenses() {return expenses;}
    public Double getMajorExpenses() {return majorExpenses;}
    public Double getCashFlow() {return income - (expenses + majorExpenses);}
    public Double getSaved() {return income - expenses;}
    public Double getSavingRate() {return savingRate;}
    public Double getGlobalRate() {return globalRate;}
}