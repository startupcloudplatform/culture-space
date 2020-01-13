package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.ANY )
@JsonIgnoreProperties( ignoreUnknown = true )
public class CulturalFacilitiesDetailResponse {

    @JsonProperty("FAC_NAME")
    private String name;

    @JsonProperty("SUBJCODE")
    private float typeCode;

    @JsonProperty("CODENAME")
    private String typeName;

    @JsonProperty("PHNE")
    private String phone;

    @JsonProperty("HOMEPAGE")
    private String homepage;

    @JsonProperty("ADDR")
    private String address;

    @JsonProperty("X_COORD")
    private Float xCoord;

    @JsonProperty("Y_COORD")
    private Float yCoord;

    @JsonProperty("ENTR_FEE")
    private String fee;

    @JsonProperty("ENTRFREE")
    private int free;

    public CulturalFacilitiesDetailResponse(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public float getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(float typeCode) {
        this.typeCode = typeCode;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }
}
