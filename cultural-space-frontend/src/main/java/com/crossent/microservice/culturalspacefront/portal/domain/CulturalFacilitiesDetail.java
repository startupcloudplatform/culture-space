package com.crossent.microservice.culturalspacefront.portal.domain;

import java.util.Date;

public class CulturalFacilitiesDetail {

    private long id;
    private String name;
    private FacilityType type;
    private String phone;
    private String homepage;
    private String address;
    private String enterFee;
    private String free;
    private float xCoord;
    private float yCoord;
    private Date created;


    public CulturalFacilitiesDetail(){};

    public CulturalFacilitiesDetail(String name, FacilityType type, String phone, String homepage, String address, String enterFee, Float xCoord, Float yCoord, String free) {
        this.name = name;
        this.type = type;
        this.phone = phone;
        this.homepage = homepage;
        this.address = address;
        this.enterFee = enterFee;
        this.free = free;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.created = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FacilityType getType() {
        return type;
    }

    public void setType(FacilityType type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEnterFee() {
        return enterFee;
    }

    public void setEnterFee(String enterFee) {
        this.enterFee = enterFee;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public float getxCoord() {
        return xCoord;
    }

    public void setxCoord(float xCoord) {
        this.xCoord = xCoord;
    }

    public float getyCoord() {
        return yCoord;
    }

    public void setyCoord(float yCoord) {
        this.yCoord = yCoord;
    }

}
