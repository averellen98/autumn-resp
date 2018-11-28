package com.project.group.group_project;

public class Availability {

    private String id;
    private String serviceProviderId;
    private Util.WeekDay weekDay;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;

    public Availability(String id, String serviceProviderId, Util.WeekDay weekDay, int startHour, int startMinute, int endHour, int endMinute) {

        this.id = id;
        this.serviceProviderId = serviceProviderId;
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Util.WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Util.WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}