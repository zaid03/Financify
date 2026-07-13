package com.financify.models;

public class GoalSummaryModel {
    private Integer totalTarget;
    private Integer totalCurrent;
    private Integer totalRemaining;

    public GoalSummaryModel(Integer totalTarget, Integer totalCurrent, Integer totalRemaining) {
        this.totalTarget = totalTarget;
        this.totalCurrent = totalCurrent;
        this.totalRemaining = totalRemaining;
    }

    public Integer getTotalTarget() {return totalTarget;}
    public Integer getTotalCurrent() {return totalCurrent;}
    public Integer getTotalRemaining() {return totalRemaining;}
}