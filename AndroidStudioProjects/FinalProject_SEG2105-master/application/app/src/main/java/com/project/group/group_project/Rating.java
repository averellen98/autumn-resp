package com.project.group.group_project;

public class Rating {

    private String id;
    private String comment;
    private String serviceId;

    private int rate;

    public Rating(String id, String serviceId, String comment, int rate) {

        this.id = id;
        this.serviceId = serviceId;
        this.comment = comment;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}