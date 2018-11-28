package com.project.group.group_project;

public class Booking {

    private String id;
    private String homeOwnerId;
    private String serviceId;
    private String serviceProviderId;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int day;
    private int month;
    private int year;

    public Booking(String id, String homeOwnerId, String serviceId, String serviceProviderId, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {

        this.id = id;
        this.homeOwnerId = homeOwnerId;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public String getHomeOwnerId() {
        return homeOwnerId;
    }

    public void setHomeOwnerId(String homeOwnerId) {
        this.homeOwnerId = homeOwnerId;
    }

    public String getId() {
        return id;
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

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}