package com.project.group.group_project;

/**
 * Represents services that a home owner may request.
 */
public class Service {

    private String name;
    private String description;
    private String id;

    /**
     * We represent money as an integer, the last two digits will be the decimal value.
     * Therefore 1$ is 100.
     */
    private int ratePerHour;

    public Service(String id, String name, String description, int ratePerHour) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ratePerHour = ratePerHour;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRatePerHour() {
        return ratePerHour;
    }

    public void setRatePerHour(int ratePerHour) {
        this.ratePerHour = ratePerHour;
    }

    public String getId() {
        return id;
    }

    public boolean isValidService(Service service){
        if (service.getId() != "" && service.getRatePerHour() != 0 && service.getDescription() != "" && service.getName() != ""){
            return true;
        }
        else {
            return false;
        }
    }
}
