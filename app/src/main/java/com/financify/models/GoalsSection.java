package com.financify.models;

public class GoalsSection {
    private Integer id;
    private String goal;
    private Integer target;
    private Integer remaining;
    private Integer current;
    private String deadline;

    public GoalsSection(Integer id, String goal, Integer target, Integer remaining, Integer current, String deadline) {
        this.id = id;
        this.goal = goal;
        this.target = target;
        this.remaining = remaining;
        this.current = current;
        this.deadline = deadline;
    }

    public Integer getId() {return id;}
    public String getGoal() {return goal;}
    public Integer getTarget() {return target;}
    public Integer getRemaining() {return remaining;}
    public Integer getCurrent() {return current;}
    public String getDeadline() {return deadline;}
}