package com.crossent.microservice.culturalspacefront.portal.domain;

public class FacilityType {

    private long code;
    private String name;

    public FacilityType(){};

    public FacilityType(long code, String name){
        this.code = code;
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
