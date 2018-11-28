package com.project.group.group_project;

public class Address {

    private String id;
    private String serviceProviderId;
    private String street;
    private String city;
    private String province;
    private String postalCode;

    public Address(String id, String serviceProviderId, String street, String city, String province, String postalCode) {

        this.id = id;
        this.serviceProviderId = serviceProviderId;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.province = province;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}