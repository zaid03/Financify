package com.financify.models;

public class GoalsSection {
    private Integer id;
    private String goal;
    private Integer target;
    private Integer current;
    private String deadline;
    private Integer is_active;
    private String completionDate;

    public GoalsSection(Integer id, String goal, Integer target , Integer current, String deadline, Integer is_active, String completionDate) {
        this.id = id;
        this.goal = goal;
        this.target = target;
        this.current = current;
        this.deadline = deadline;
        this.is_active = is_active;
        this.completionDate = completionDate;
    }

    public Integer getId() {return id;}
    public String getGoal() {return goal;}
    public Integer getTarget() {return target;}
    public Integer getRemaining() {return target - current;}
    public Integer getCurrent() {return current;}
    public String getDeadline() {return deadline;}
    public Integer getIs_active() {return is_active;}
    public String getCompletionDate() {return completionDate;}
}